package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(SysUserRepository sysUserRepository, PasswordEncoder passwordEncoder) {
        this.sysUserRepository = sysUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SysUser saveUser(SysUser sysUser) {
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        return sysUserRepository.save(sysUser);
    }

    @Override
    @Transactional
    public SysUser updateUser(SysUser sysUser) {
        if (sysUser.getPassword() != null && !sysUser.getPassword().isEmpty()) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        } else {
            SysUser existing = sysUserRepository.findById(sysUser.getId()).orElseThrow();
            sysUser.setPassword(existing.getPassword());
        }
        return sysUserRepository.save(sysUser);
    }

    @Override
    @Transactional
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

    @Override
    public SysUser findById(long id) {
        return sysUserRepository.findById(id).orElse(null);
    }
}
