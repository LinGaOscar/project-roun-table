package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public SysUser saveUser(SysUser sysUser) {
        // Encrypt password before saving
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        return sysUserRepository.save(sysUser);
    }

    @Override
    public SysUser updateUser(SysUser sysUser) {
        // If password is changed, it should be re-encoded
        // For simplicity in this implementation, we re-encode
        if (sysUser.getPassword() != null && !sysUser.getPassword().isEmpty()) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
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

    @Override
    public SysUser findById(long id) {
        return sysUserRepository.findById(id).orElse(null);
    }
}
