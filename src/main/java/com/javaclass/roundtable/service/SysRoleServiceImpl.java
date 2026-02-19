package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysRole;
import com.javaclass.roundtable.repository.SysRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleRepository sysRoleRepository;

    public SysRoleServiceImpl(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    @Override
    public SysRole findByRole(String role) {
        return sysRoleRepository.findByRole(role);
    }
}
