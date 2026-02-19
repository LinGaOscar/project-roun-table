package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.Enrollment;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.EnrollmentRepository;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/myClasses")
public class MyClassesController {
    private final EnrollmentRepository enrollmentRepository;
    private final SysUserService sysUserService;

    public MyClassesController(EnrollmentRepository enrollmentRepository, SysUserService sysUserService) {
        this.enrollmentRepository = enrollmentRepository;
        this.sysUserService = sysUserService;
    }

    @GetMapping()
    public String myClassesPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Removed manual session check; Spring Security handles this now
        String account = userDetails.getUsername();
        SysUser user = sysUserService.findByAccount(account);
        
        List<Enrollment> enrollments = enrollmentRepository.findByUserOrderByEnrollmentDateDesc(user);
        model.addAttribute("enrollments", enrollments);
        
        return "my_classes";
    }
}
