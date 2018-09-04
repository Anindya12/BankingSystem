package com.nostratech.project.persistence.vo;

import lombok.Data;

import java.util.Currency;

@Data
public class AccountVO extends BaseVO {
   // private Integer id;

    private String accountId;

    private Double balance;
}
