package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SysUserService {

    SysUser saveUser(SysUser sysUser);

    SysUser updateUser(SysUser sysUser);

    void deleteUser(long id);

    List<SysUser> findAll();

    Page<SysUser> findAll(Pageable pageable);

    SysUser findByAccount(String account);

    SysUser findById(long id);
}
