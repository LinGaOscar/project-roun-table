package com.javaclass.roundtable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping({"/"})
    public String index(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("account") String account,@RequestParam("password") String password){
        return "index";
    }
}
