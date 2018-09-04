package com.nostratech.project.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Entity
@Table(name = "Transfer")
@DynamicUpdate
@Data
public class Transfer extends Base {

    @ManyToOne
    @JoinColumn(name = "AccountId_Source")
    private Account accountIdSource;

    @ManyToOne
    @JoinColumn(name = "AccountId_Dest")
    private Account accountIdDest;

    @Column(name = "Balance")
    private Double balance;
}
