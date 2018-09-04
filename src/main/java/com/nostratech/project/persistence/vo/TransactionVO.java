package com.nostratech.project.persistence.vo;

import com.nostratech.project.persistence.domain.Account;
import lombok.Data;

@Data
public class TransactionVO extends BaseVO{
    private String accountId;

    private Double balance;

    //private String status;
}
