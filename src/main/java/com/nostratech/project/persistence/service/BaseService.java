package com.nostratech.project.persistence.service;

import com.nostratech.project.exception.NostraException;
import com.nostratech.project.persistence.domain.Base;
import com.nostratech.project.persistence.vo.BaseVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yukibuwana on 1/24/17.
 */
@Transactional(readOnly = true)
public abstract class BaseService<T extends Base, V extends BaseVO, Z> extends AbstractBaseService<T,V, Z> {

    protected V add(T t) {
        T updated = getJpaRepository().save(t);

        if (null != updated.getId()) {
            V vo = getVoConverter().transferModelToVO(updated, null);

            if(null == updated.getSecureId() || updated.getSecureId().isEmpty()) {
                throw new NostraException("Entity doesn't have secure id");
            }

            vo.setId(updated.getSecureId());
            return vo;
        }
        return null;
    }

    @Transactional
    @Override
    public V add(Z vo) {
        T t = getVoConverter().transferVOToModel(vo, null);
        return this.add(t);
    }

    @Transactional
    @Override
    public V update(String secureKey, Z vo) {
        T t = getJpaRepository().findBySecureId(secureKey);
        return this.update(t, vo);
    }

    @Transactional
    @Override
    public V update(Integer id, Z vo) {
        T t = getJpaRepository().findOne(id);
        return this.update(t, vo);
    }

    @Transactional
    protected V update(T t, Z vo) {
        if (null == t) {
            throw new NostraException("Entity not found for update");
        }

        getVoConverter().transferVOToModel(vo, t);

        T updateObj = getJpaRepository().saveAndFlush(t);

        if (null != updateObj) {
            return getVoConverter().transferModelToVO(t, null);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean delete(String secureKey) {
        T t = getJpaRepository().findBySecureId(secureKey);

        if (null != t) {
            Integer id = t.getId();
            getJpaRepository().delete(id);
            if (!getJpaRepository().exists(id)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public V findBySecureId(String secureKey) {
        T t = getJpaRepository().findBySecureId(secureKey);

        if(t != null)
            return getVoConverter().transferModelToVO(t, null);
        else
            return null;
    }

    @Override
    public V findById(Integer id) {

        T t = getJpaRepository().findById(id);

        if(t != null)
            return getVoConverter().transferModelToVO(t, null);
        else
            return null;
    }
}
