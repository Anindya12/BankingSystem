package com.nostratech.project.converter;

import com.nostratech.project.persistence.domain.User;
import com.nostratech.project.persistence.vo.UserVO;
import com.nostratech.project.util.ExtendedSpringBeanUtil;
import org.springframework.stereotype.Component;

@Component
public class UserVOConverter extends BaseVoConverter<UserVO, UserVO, User> implements IBaseVoConverter<UserVO, UserVO, User>{

    @Override
    public UserVO transferModelToVO(User model, UserVO vo) {
        if (null == vo) vo = new UserVO();

        super.transferModelToVO(model, vo);
        ExtendedSpringBeanUtil.copySpecificProperties(model, vo,
                new String[]{"fullName", "address", "telephone", "email", "status", "username", "password", "confirmPassword"}
        );

        return vo;
    }

    @Override
    public User transferVOToModel(UserVO vo, User model) {
        if (null == model) model = new User();

        ExtendedSpringBeanUtil.copySpecificProperties(vo, model,
                new String[]{"fullName", "address", "telephone", "email", "status", "username", "password", "confirmPassword"});

        return model;
    }
}
