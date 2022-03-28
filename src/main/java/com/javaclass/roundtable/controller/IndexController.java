package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class IndexController {
    @GetMapping({"/"})
    public String indexPage() {
//        return "login";
        return "test";
    }

    @GetMapping({"/timeTable"})
    public String timeTablePage() {
        return "class_table";
    }

    @GetMapping({"/index"})
    public String loginPage(Model model) {
        SysUser sysUser = (SysUser) model.asMap().get("user");
        System.out.println(model.addAttribute("user"));
        System.out.println(sysUser);
        if (Objects.isNull(sysUser)) {

        }
//        System.out.println(model.getAttribute("user"));
        return "index";
    }
}
