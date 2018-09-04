package com.nostratech.project.persistence.vo;

import lombok.Data;

@Data
public class TransferVO extends BaseVO{
    private String accountIdSource;

    private String accountIdDest;

    private Double balance;
}
