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
    private SysUserServiceImpl sysUserService;
    private ClassTableServiceImpl classTableService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService, ClassTableServiceImpl classTableService) {
        this.sysUserService = sysUserService;
        this.classTableService = classTableService;
    }


    @GetMapping({"/"})
    public String indexPage() {
        return "login";
//        return "test";
    }

    @GetMapping({"/classTable"})
    public String timeTablePage(Model model) {
        List<ClassTable> classTables = classTableService.findAll();
        model.addAttribute("classList", classTables);

        return "class_table";
    }

    @GetMapping({"/userTable"})
    public String userTablePage(Model model) {
        List<SysUser> sysUsers = sysUserService.findAll();
        model.addAttribute("userList", sysUsers);

        return "user_table";
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
