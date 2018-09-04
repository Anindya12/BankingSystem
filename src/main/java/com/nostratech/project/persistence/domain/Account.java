package com.nostratech.project.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Currency;

@Entity
@Table(name = "Account")
@DynamicUpdate
@Data
public class Account extends Base {
//public class Account implements Serializable{
//    @Id
//    //@GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "Account_Id")
//    private Long accountId;

//    @JoinColumn(name = "Account_Id")
//    private Account accountId;

    @Column(name = "Balance")
    private Double balance;
}
