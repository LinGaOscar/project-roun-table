package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/api")
public class LoginController {
    private SysUserServiceImpl sysUserService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String account, @RequestParam String password, Model model, RedirectAttributes redirectAttrs) {
        System.out.println(account + password);
        SysUser sysUser = sysUserService.findByAccount(account);
        System.out.println(sysUser);
        if (Objects.isNull(sysUser)) {
            model.addAttribute("userError", "not found user");
            return "login";
        } else {
            if (sysUser.getPassword().equals(password)) {
                redirectAttrs.addFlashAttribute("user", sysUser);
                return "redirect:/index";
            } else {
                model.addAttribute("passwordError", "incorrect password");
                return "login";
            }
        }
    }
}
