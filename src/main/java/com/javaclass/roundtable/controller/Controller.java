package com.javaclass.roundtable.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/hello")
    public Map<String, String> SayHello() {
        Map<String, String> result = Map.of("message", "say hello");
        return result;
    }

    @GetMapping("/user/hello")
    public Map<String, String> userSayHello() {
        Map<String, String> result = Map.of("message", "user say hello");
        return result;
    }

    @GetMapping("/admin/hello")
    public Map<String, String> adminSayHello() {
        Map<String, String> result = Map.of("message", "admin say hello");
        return result;
    }

}