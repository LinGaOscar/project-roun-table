package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    private SysUserServiceImpl sysUserService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService) {
        this.sysUserService = sysUserService;
    }


    @GetMapping({"/"})
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password) {

        SysUser sysUser = sysUserService.findByAccount("account");
        if (sysUser == null) {
            return "login";
        }else{
            return "index";
        }


    }
}
