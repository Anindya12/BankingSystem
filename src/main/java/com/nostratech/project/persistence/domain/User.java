package com.nostratech.project.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User")
@DynamicUpdate
@Data
public class User extends Base {
    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Telephone")
    private Long telephone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Status")
    private String status;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Confirm_Password")
    private String confirmPassword;

    @OneToOne
    @JoinColumn(name = "Account_Id")
    private Account accountId;

}
