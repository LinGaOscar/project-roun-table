package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private final SysUserService sysUserService;

    public AdminUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping
    public String userTablePage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<SysUser> userPage = sysUserService.findAll(PageRequest.of(page, 10));
        model.addAttribute("userList", userPage.getContent());
        model.addAttribute("page", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "admin/user_table";
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new SysUser());
        return "admin/user_edit";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") SysUser sysUser, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/user_edit";
        }
        if (sysUser.getPassword() == null || sysUser.getPassword().length() < 6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters");
            return "admin/user_edit";
        }
        if (sysUserService.findByAccount(sysUser.getAccount()) != null) {
            model.addAttribute("accountError", "Duplicate account: " + sysUser.getAccount());
            return "admin/user_edit";
        }
        sysUserService.saveUser(sysUser);
        redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
        return "redirect:/admin/user";
    }

    @GetMapping("/edit/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        SysUser sysUser = sysUserService.findById(id);
        if (sysUser == null) {
            throw new BusinessException("User not found for ID: " + id);
        }
        model.addAttribute("user", sysUser);
        return "admin/user_edit";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute("user") SysUser sysUser, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/user_edit";
        }
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
