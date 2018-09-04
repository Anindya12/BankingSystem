package com.nostratech.project.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Entity
@Table(name = "Transaction")
@DynamicUpdate
@Data
public class Transaction extends Base{

    @ManyToOne
    @JoinColumn(name = "Account_Id")
    private Account accountId;

    @Column(name = "Balance")
    private Double balance;

    @Column(name = "Status")
    private String status;
}
