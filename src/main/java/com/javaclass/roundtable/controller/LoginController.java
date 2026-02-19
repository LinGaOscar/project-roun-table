package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysRole;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.SysRoleService;
import com.javaclass.roundtable.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class LoginController {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    public LoginController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String account, @RequestParam String password, Model model, HttpSession httpSession) {
        log.info("Login attempt for account: {}", account);
        
        if ("ok".equals(httpSession.getAttribute("loginCheck"))) {
            return "redirect:/index";
        }

        SysUser sysUser = sysUserService.findByAccount(account);
        if (Objects.isNull(sysUser)) {
            log.warn("Login failed: User {} not found", account);
            model.addAttribute("userError", "not found user");
            return "login";
        }
        
        if (sysUser.getPassword().equals(password)) {
            SysRole sysRole = sysRoleService.findByRole(sysUser.getRole());
            httpSession.setAttribute("loginCheck", "ok");
            httpSession.setAttribute("userName", sysUser.getUserName());
            httpSession.setAttribute("userAccount", sysUser.getAccount());
            if (sysRole != null) {
                httpSession.setAttribute("role", sysRole.getFunction());
            }
            log.info("User {} logged in successfully", account);
            return "redirect:/index";
        } else {
            log.warn("Login failed: Incorrect password for user {}", account);
            model.addAttribute("passwordError", "incorrect password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        String account = (String) httpSession.getAttribute("userAccount");
        log.info("User {} logged out", account);
        httpSession.invalidate();
        return "login";
    }
}
