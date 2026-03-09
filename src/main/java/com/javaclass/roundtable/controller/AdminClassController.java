package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.SysUserService;
import com.javaclass.roundtable.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    public String classListPage(Model model) {
        List<ClassTable> classTables = classTableService.findAllOrderBySeqNo();
        model.addAttribute("classList", classTables);
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
    public String saveClass(@ModelAttribute ClassTable classTable, RedirectAttributes redirectAttributes) {
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
