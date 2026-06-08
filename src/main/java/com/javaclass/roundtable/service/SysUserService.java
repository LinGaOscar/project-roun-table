package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import java.util.List;

public interface SysUserService {


    SysUser saveUser(SysUser sysUser);

    SysUser updateUser(SysUser sysUser);

    void deleteUser(long id);

    List<SysUser> findAll();

    SysUser findByAccount(String account);

    SysUser findById(long id);
}
