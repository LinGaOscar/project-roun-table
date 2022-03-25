package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @GetMapping({"/"})
    public String indexPage() {
        return "login";
//        return "test";
    }

    @GetMapping({"/timeTable"})
    public String timeTablePage() {
        return "timetable";
    }

    @GetMapping({"/index"})
    public String loginPage() {
        return "index";
    }
}
