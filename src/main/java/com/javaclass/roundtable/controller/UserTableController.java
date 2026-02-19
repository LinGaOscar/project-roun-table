package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/userTable")
public class UserTableController {
    private final SysUserService sysUserService;

    public UserTableController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping()
    public String userTablePage(Model model) {
        List<SysUser> sysUsers = sysUserService.findAll();
        model.addAttribute("userList", sysUsers);
        return "user_table";
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new SysUser());
        return "user_add_table";
    }

    @PostMapping("/add")
    public String addUser(SysUser sysUser, Model model) {
        if (sysUserService.findByAccount(sysUser.getAccount()) == null) {
            sysUserService.saveUser(sysUser);
            return "redirect:/userTable";
        } else {
            model.addAttribute("accountError", "Duplicate account: " + sysUser.getAccount());
            return "user_add_table";
        }
    }

    @GetMapping("/update/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        SysUser sysuser = sysUserService.findById(id);
        if (sysuser == null) {
            throw new BusinessException("User not found for ID: " + id);
        }
        model.addAttribute("user", sysuser);
        return "user_add_table";
    }

    @PostMapping("/update")
    public String updateUser(SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return "redirect:/userTable";
    }

    @GetMapping("/delete/{id}")
    public String deleleUser(@PathVariable("id") long id) {
        sysUserService.deleteUser(id);
        return "redirect:/userTable";
    }
}
