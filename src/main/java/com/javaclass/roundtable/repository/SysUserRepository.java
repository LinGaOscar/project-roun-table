package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByAccount(String account);
}
