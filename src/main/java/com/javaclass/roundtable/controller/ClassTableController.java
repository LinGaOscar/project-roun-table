package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.entity.Venue;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.EnrollmentService;
import com.javaclass.roundtable.service.SysUserService;
import com.javaclass.roundtable.service.VenueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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
    public String timeTablePage(Model model, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        List<ClassTable> classTables = classTableService.findAllOrderBySeqNo();
        model.addAttribute("classList", classTables);
        return "class_table";
    }

    @PostMapping("/enroll/{id}")
    public String enroll(@PathVariable("id") long classId, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        try {
            String account = (String) httpSession.getAttribute("userAccount");
            SysUser user = sysUserService.findByAccount(account);
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
        ClassTable classTable;
        if (id != null) {
            classTable = classTableService.findById(id);
            if (classTable == null) throw new BusinessException("Class not found.");
        } else {
            classTable = new ClassTable();
        }
        
        model.addAttribute("classTable", classTable);
        model.addAttribute("venues", venueService.findAll());
        // Filter users who are lecturers - assuming 'role' contains 'lecturer' or similar
        // For now, listing all users to allow selection
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
