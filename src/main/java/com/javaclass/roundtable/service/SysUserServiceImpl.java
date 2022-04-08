package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    private SysUserRepository sysUserRepository;

    @Autowired
    public void autoWired(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public SysUser saveUser(SysUser sysUser) {
        return sysUserRepository.save(sysUser);
    }

    @Override
    public SysUser updateUser(SysUser sysUser) {
        return sysUserRepository.save(sysUser);
    }

    @Override
    public void deleteUser(long id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public SysUser findByAccount(String account) {
        return sysUserRepository.findByAccount(account);
    }
}
