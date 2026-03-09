package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private final SysUserService sysUserService;

    public AdminUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping
    public String userTablePage(Model model) {
        List<SysUser> sysUsers = sysUserService.findAll();
        model.addAttribute("userList", sysUsers);
        return "admin/user_table";
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new SysUser());
        return "admin/user_edit";
    }

    @PostMapping("/add")
    public String addUser(SysUser sysUser, Model model, RedirectAttributes redirectAttributes) {
        if (sysUserService.findByAccount(sysUser.getAccount()) == null) {
            sysUserService.saveUser(sysUser);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
            return "redirect:/admin/user";
        } else {
            model.addAttribute("accountError", "Duplicate account: " + sysUser.getAccount());
            return "admin/user_edit";
        }
    }

    @GetMapping("/edit/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        SysUser sysuser = sysUserService.findById(id);
        if (sysuser == null) {
            throw new BusinessException("User not found for ID: " + id);
        }
        model.addAttribute("user", sysuser);
        return "admin/user_edit";
    }

    @PostMapping("/edit")
    public String updateUser(SysUser sysUser, RedirectAttributes redirectAttributes) {
        sysUserService.updateUser(sysUser);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            sysUserService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            log.error("Error deleting user", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/user";
    }
}
