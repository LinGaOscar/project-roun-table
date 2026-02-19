package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account", unique = true, nullable = false)
    private String account;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    // --- Lecturer Specific Fields ---
    @Column(name = "bio", length = 1000)
    private String bio;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
