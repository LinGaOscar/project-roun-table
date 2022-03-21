package com.javaclass.roundtable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class TimeTableController {

    @GetMapping({"/getTable"})
    public String index(){
        return "timetable";
    }
}
