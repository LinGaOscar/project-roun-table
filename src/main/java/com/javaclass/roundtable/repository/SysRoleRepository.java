package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole,Long> {
    SysRole findByRole(String role);
}
