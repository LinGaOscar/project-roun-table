package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysRole;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysRoleServiceImpl;
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
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    public void autoWired(SysUserServiceImpl sysUserService ,SysRoleServiceImpl sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
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
            SysRole sysRole = sysRoleService.findByRole(sysUser.getRole());
            httpSession.setAttribute("loginCheck", "ok");
            httpSession.setAttribute("userName", sysUser.getUserName());
            httpSession.setAttribute("userAccount", sysUser.getAccount());
            httpSession.setAttribute("role", sysRole.getFunction());
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
        httpSession.removeAttribute("role");
        //httpSession.invalidate();
        return "login";
    }
}
