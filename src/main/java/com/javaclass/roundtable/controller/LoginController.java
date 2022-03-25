package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class LoginController {
    private SysUserServiceImpl sysUserService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String account, @RequestParam String password) {
//        System.out.println(account + password);
        SysUser sysUser = sysUserService.findByAccount("account");
        if (sysUser == null) {
            return "login";
        } else {
            return "index";
        }
    }
}
