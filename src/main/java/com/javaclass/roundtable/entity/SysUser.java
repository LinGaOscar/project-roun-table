package com.javaclass.roundtable.entity;

import lombok.Data;

import javax.persistence.*;
import java.net.ProtocolFamily;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}
