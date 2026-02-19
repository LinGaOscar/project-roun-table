package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.Enrollment;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.EnrollmentRepository;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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
    public String myClassesPage(Model model, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        
        String account = (String) httpSession.getAttribute("userAccount");
        SysUser user = sysUserService.findByAccount(account);
        
        List<Enrollment> enrollments = enrollmentRepository.findByUserOrderByEnrollmentDateDesc(user);
        model.addAttribute("enrollments", enrollments);
        
        return "my_classes";
    }
}
