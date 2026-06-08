package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Account is required")
    @Size(min = 3, max = 50, message = "Account must be 3–50 characters")
    @Column(name = "account", unique = true, nullable = false)
    private String account;

    @NotBlank(message = "Name is required")
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
