package com.nostratech.project.persistence.vo;

import com.nostratech.project.persistence.domain.Account;
import lombok.Data;

import java.util.Collection;


@Data
public class UserVO extends BaseVO {
    private String fullName;

    private String address;

    private Long telephone;

    private String email;

    private String status;

    private String username;

    private String password;

    private String confirmPassword;

    private Collection<AccountVO> accountId;

}
