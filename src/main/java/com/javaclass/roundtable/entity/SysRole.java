package com.javaclass.roundtable.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_Role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "function")
    private String function;
}
