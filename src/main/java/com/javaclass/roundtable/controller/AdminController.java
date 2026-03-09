package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.service.ClassTableService;
import com.javaclass.roundtable.service.VenueService;
import com.javaclass.roundtable.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ClassTableService classTableService;
    private final VenueService venueService;
    private final SysUserService sysUserService;

    public AdminController(ClassTableService classTableService, 
                          VenueService venueService, 
                          SysUserService sysUserService) {
        this.classTableService = classTableService;
        this.venueService = venueService;
        this.sysUserService = sysUserService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalClasses", classTableService.findAll().size());
        model.addAttribute("totalVenues", venueService.findAll().size());
        model.addAttribute("totalUsers", sysUserService.findAll().size());
        return "admin/index";
    }
}
