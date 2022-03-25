package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.service.ClassTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ClassTableController {
    private ClassTableServiceImpl classTableService;

    @Autowired
    public void autoWired(ClassTableServiceImpl classTableService) {
        this.classTableService = classTableService;
    }



    @GetMapping({"/getTable"})
    @ResponseBody
    public List<ClassTable> getAllTable() {
        return classTableService.findAll();
    }
}
