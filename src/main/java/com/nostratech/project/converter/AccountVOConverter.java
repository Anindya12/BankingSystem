package com.nostratech.project.converter;

import com.nostratech.project.persistence.domain.Account;
import com.nostratech.project.persistence.vo.AccountVO;
import com.nostratech.project.util.ExtendedSpringBeanUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountVOConverter extends BaseVoConverter<AccountVO, AccountVO, Account> implements IBaseVoConverter<AccountVO, AccountVO, Account>{

    @Override
    public AccountVO transferModelToVO(Account model, AccountVO vo) {
        if (null == vo) vo = new AccountVO();

        super.transferModelToVO(model, vo);
        ExtendedSpringBeanUtil.copySpecificProperties(model, vo,
                new String[]{"accountId","balance"}
        );

        return vo;
    }

    @Override
    public Account transferVOToModel(AccountVO vo, Account model) {
        if (null == model) model = new Account();

        ExtendedSpringBeanUtil.copySpecificProperties(vo, model,
                new String[]{"accountId","balance"});

        return model;
    }
}
