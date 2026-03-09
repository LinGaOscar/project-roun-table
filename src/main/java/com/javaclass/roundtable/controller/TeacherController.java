package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.SysUserService;
import com.javaclass.roundtable.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final ClassTableService classTableService;
    private final VenueService venueService;
    private final SysUserService sysUserService;

    public TeacherController(ClassTableService classTableService,
                            VenueService venueService,
                            SysUserService sysUserService) {
        this.classTableService = classTableService;
        this.venueService = venueService;
        this.sysUserService = sysUserService;
    }

    @GetMapping("/class")
    public String myClassesPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        SysUser teacher = sysUserService.findByAccount(userDetails.getUsername());
        List<ClassTable> myClasses = classTableService.findByInstructorId(teacher.getId());
        model.addAttribute("classList", myClasses);
        return "teacher/my_courses";
    }

    @GetMapping("/class/add")
    public String addClassPage(Model model) {
        model.addAttribute("classTable", new ClassTable());
        model.addAttribute("venues", venueService.findAll());
        return "teacher/course_edit";
    }

    @GetMapping("/class/edit/{id}")
    public String editClassPage(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ClassTable classTable = classTableService.findById(id);
        SysUser teacher = sysUserService.findByAccount(userDetails.getUsername());
        
        if (!classTable.getInstructor().getId().equals(teacher.getId())) {
            model.addAttribute("errorMessage", "You can only edit your own courses!");
            return "redirect:/teacher/class";
        }
        
        model.addAttribute("classTable", classTable);
        model.addAttribute("venues", venueService.findAll());
        return "teacher/course_edit";
    }

    @PostMapping("/class/save")
    public String saveClass(@ModelAttribute ClassTable classTable, 
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        try {
            SysUser teacher = sysUserService.findByAccount(userDetails.getUsername());
            classTable.setInstructor(teacher);
            
            if (classTable.getId() != null) {
                ClassTable existing = classTableService.findById(classTable.getId());
                if (!existing.getInstructor().getId().equals(teacher.getId())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "You can only edit your own courses!");
                    return "redirect:/teacher/class";
                }
                classTableService.updateTable(classTable);
                redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
            } else {
                classTableService.saveTable(classTable);
                redirectAttributes.addFlashAttribute("successMessage", "Course created successfully!");
            }
        } catch (Exception e) {
            log.error("Error saving course", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving course: " + e.getMessage());
        }
        return "redirect:/teacher/class";
    }

    @GetMapping("/class/delete/{id}")
    public String deleteClass(@PathVariable Long id, 
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            SysUser teacher = sysUserService.findByAccount(userDetails.getUsername());
            ClassTable classTable = classTableService.findById(id);
            
            if (!classTable.getInstructor().getId().equals(teacher.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "You can only delete your own courses!");
                return "redirect:/teacher/class";
            }
            
            classTableService.deleteTable(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (Exception e) {
            log.error("Error deleting course", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/teacher/class";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        SysUser teacher = sysUserService.findByAccount(userDetails.getUsername());
        model.addAttribute("teacher", teacher);
        return "teacher/profile";
    }
}
