package com.javaclass.roundtable.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "account")
    private String account;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;
}
