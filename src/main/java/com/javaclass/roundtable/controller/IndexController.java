package com.javaclass.roundtable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String indexPage() {
        // Simplified: Security is now handled by WebSecurityConfig
        return "index";
    }

}
