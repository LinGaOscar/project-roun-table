package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.service.ClassTableService;
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

    public ClassTableController(ClassTableService classTableService) {
        this.classTableService = classTableService;
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
