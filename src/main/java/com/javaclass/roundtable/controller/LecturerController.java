package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.repository.ClassTableRepository;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {
    private final SysUserService sysUserService;
    private final ClassTableRepository classTableRepository;

    public LecturerController(SysUserService sysUserService, ClassTableRepository classTableRepository) {
        this.sysUserService = sysUserService;
        this.classTableRepository = classTableRepository;
    }

    @GetMapping("/{id}")
    public String lecturerProfile(@PathVariable("id") long id, Model model) {
        SysUser lecturer = sysUserService.findById(id);
        if (lecturer == null) {
            throw new BusinessException("Lecturer not found.");
        }
        
        List<ClassTable> classes = classTableRepository.findByInstructor(lecturer);
        
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("classes", classes);
        return "lecturer_profile";
    }
}
