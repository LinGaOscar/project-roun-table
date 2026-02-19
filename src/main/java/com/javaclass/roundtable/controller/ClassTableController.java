package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.EnrollmentService;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/classTable")
public class ClassTableController {
    private final ClassTableService classTableService;
    private final EnrollmentService enrollmentService;
    private final SysUserService sysUserService;

    public ClassTableController(ClassTableService classTableService, 
                                EnrollmentService enrollmentService,
                                SysUserService sysUserService) {
        this.classTableService = classTableService;
        this.enrollmentService = enrollmentService;
        this.sysUserService = sysUserService;
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
    public String enroll(@PathVariable("id") long classId, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        
        String account = (String) httpSession.getAttribute("userAccount");
        SysUser user = sysUserService.findByAccount(account);
        
        enrollmentService.enroll(user.getId(), classId);
        
        return "redirect:/classTable";
    }

    @GetMapping({"/editTable/{id}"})
    public String editTablePage(@PathVariable("id") long id, Model model) {
        ClassTable classTable = classTableService.findById(id);
        if (classTable == null) {
            throw new BusinessException("Class table record not found for ID: " + id);
        }
        model.addAttribute("classTable", classTable);
        return "class_edit_table";
    }

    @PostMapping({"/editTable"})
    public String editTable(ClassTable classTable) {
        classTableService.updateTable(classTable);
        return "redirect:/classTable";
    }
}
