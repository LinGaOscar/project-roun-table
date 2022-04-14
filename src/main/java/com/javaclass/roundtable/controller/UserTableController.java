package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/userTable")
public class UserTableController {
    private SysUserServiceImpl sysUserService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping()
    public String userTablePage(Model model, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        List<SysUser> sysUsers = sysUserService.findAll();
        model.addAttribute("userList", sysUsers);
        return "user_table";
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {

        model.addAttribute("user",new SysUser());
        return "user_add_table";
    }

    @PostMapping("/add")
    public String addUser(SysUser sysUser, Model model) {
        if (Objects.isNull(sysUserService.findByAccount(sysUser.getAccount()))) {
            sysUserService.saveUser(sysUser);
            List<SysUser> sysUsers = sysUserService.findAll();
            model.addAttribute("userList", sysUsers);
            return "user_table";
        } else {
            model.addAttribute("accountError", "Duplicate account");
            return "user_add_table";
        }
    }


    @GetMapping("/update/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        SysUser sysuser = sysUserService.findById(id);
        model.addAttribute("user", sysuser);
        return "user_add_table";
    }

    @PostMapping("/update")
    public String updateUser(SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return "redirect:/userTable";

    }
}
