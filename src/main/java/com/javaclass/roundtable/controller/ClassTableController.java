package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.EnrollmentService;
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
@RequestMapping("/classTable")
public class ClassTableController {
    private final ClassTableService classTableService;
    private final EnrollmentService enrollmentService;
    private final SysUserService sysUserService;
    private final VenueService venueService;

    public ClassTableController(ClassTableService classTableService, 
                                EnrollmentService enrollmentService,
                                SysUserService sysUserService,
                                VenueService venueService) {
        this.classTableService = classTableService;
        this.enrollmentService = enrollmentService;
        this.sysUserService = sysUserService;
        this.venueService = venueService;
    }

    @GetMapping()
    public String timeTablePage(Model model) {
        List<ClassTable> classTables = classTableService.findAllOrderBySeqNo();
        model.addAttribute("classList", classTables);
        return "class_table";
    }

    @PostMapping("/enroll/{id}")
    public String enroll(@PathVariable("id") long classId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        try {
            SysUser user = sysUserService.findByAccount(userDetails.getUsername());
            enrollmentService.enroll(user.getId(), classId);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment successful!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred.");
        }
        return "redirect:/classTable";
    }

    @GetMapping({"/editTable/{id}", "/addTable"})
    public String editTablePage(@PathVariable(value = "id", required = false) Long id, Model model) {
        ClassTable classTable = (id != null) ? classTableService.findById(id) : new ClassTable();
        model.addAttribute("classTable", classTable);
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("lecturers", sysUserService.findAll());
        return "class_edit_table";
    }

    @PostMapping({"/saveTable"})
    public String saveTable(@ModelAttribute ClassTable classTable) {
        if (classTable.getId() != null) {
            classTableService.updateTable(classTable);
        } else {
            classTableService.saveTable(classTable);
        }
        return "redirect:/classTable";
    }
}
