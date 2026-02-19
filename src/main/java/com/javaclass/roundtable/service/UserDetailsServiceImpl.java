package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    public UserDetailsServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByAccount(account);
        if (sysUser == null) {
            throw new UsernameNotFoundException("User not found: " + account);
        }

        // Spring Security expects roles to be prefixed with ROLE_
        String role = sysUser.getRole();
        if (role == null) role = "USER";
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase();
        }

        return new User(
                sysUser.getAccount(),
                sysUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
