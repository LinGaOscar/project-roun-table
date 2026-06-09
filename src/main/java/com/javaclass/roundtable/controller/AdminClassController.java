package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.SysUserService;
import com.javaclass.roundtable.service.VenueService;
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
@RequestMapping("/admin/class")
public class AdminClassController {

    private final ClassTableService classTableService;
    private final VenueService venueService;
    private final SysUserService sysUserService;

    public AdminClassController(ClassTableService classTableService,
                                VenueService venueService,
                                SysUserService sysUserService) {
        this.classTableService = classTableService;
        this.venueService = venueService;
        this.sysUserService = sysUserService;
    }

    @GetMapping
    public String classListPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ClassTable> classPage = classTableService.findAllOrderBySeqNo(PageRequest.of(page, 10));
        model.addAttribute("classList", classPage.getContent());
        model.addAttribute("page", classPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", classPage.getTotalPages());
        return "admin/class_table";
    }

    @GetMapping("/add")
    public String addClassPage(Model model) {
        model.addAttribute("classTable", new ClassTable());
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("lecturers", sysUserService.findAll());
        return "admin/class_edit";
    }

    @GetMapping("/edit/{id}")
    public String editClassPage(@PathVariable Long id, Model model) {
        ClassTable classTable = classTableService.findById(id);
        model.addAttribute("classTable", classTable);
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("lecturers", sysUserService.findAll());
        return "admin/class_edit";
    }

    @PostMapping("/save")
    public String saveClass(@Valid @ModelAttribute ClassTable classTable, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            model.addAttribute("lecturers", sysUserService.findAll());
            return "admin/class_edit";
        }
        try {
            if (classTable.getId() != null) {
                classTableService.updateTable(classTable);
                redirectAttributes.addFlashAttribute("successMessage", "Class updated successfully!");
            } else {
                classTableService.saveTable(classTable);
                redirectAttributes.addFlashAttribute("successMessage", "Class created successfully!");
            }
        } catch (Exception e) {
            log.error("Error saving class", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving class: " + e.getMessage());
        }
        return "redirect:/admin/class";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classTableService.deleteTable(id);
            redirectAttributes.addFlashAttribute("successMessage", "Class deleted successfully!");
        } catch (Exception e) {
            log.error("Error deleting class", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting class: " + e.getMessage());
        }
        return "redirect:/admin/class";
    }
}
