package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {
    private SysUserServiceImpl sysUserService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String account, @RequestParam String password, Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("loginCheck") == "ok") {
            return "redirect:/index";
        }

        SysUser sysUser = sysUserService.findByAccount(account);
        if (Objects.isNull(sysUser)) {
            model.addAttribute("userError", "not found user");
            return "login";
        }
        if (sysUser.getPassword().equals(password)) {
            httpSession.setAttribute("loginCheck", "ok");
            httpSession.setAttribute("userName", sysUser.getUserName());
            httpSession.setAttribute("userAccount", sysUser.getAccount());
            return "redirect:/index";
        } else {
            model.addAttribute("passwordError", "incorrect password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("loginCheck");
        httpSession.removeAttribute("userName");
        httpSession.removeAttribute("userAccount");
        return "login";
    }
}
