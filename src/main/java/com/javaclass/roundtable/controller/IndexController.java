package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableServiceImpl;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class IndexController {

    @GetMapping({"/"})
    public String indexPage() {
        return "login";
//        return "test";
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
