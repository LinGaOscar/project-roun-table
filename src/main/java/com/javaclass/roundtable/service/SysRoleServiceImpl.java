package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysRole;
import com.javaclass.roundtable.repository.SysRoleRepository;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService{

    private SysRoleRepository sysUserRepository;

    @Autowired
    public void autoWired(SysRoleRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public SysRole findByRole(String role) {
        return sysUserRepository.findByRole(role);
    }
}
