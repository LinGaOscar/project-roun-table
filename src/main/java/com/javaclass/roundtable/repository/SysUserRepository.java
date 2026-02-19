package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByAccount(String account);
    List<SysUser> findByRole(String role);
}
