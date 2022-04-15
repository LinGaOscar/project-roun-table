package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/classTable")
public class ClassTableController {
    private ClassTableServiceImpl classTableService;

    @Autowired
    public void autoWired(ClassTableServiceImpl classTableService) {
        this.classTableService = classTableService;
    }

    @GetMapping()
    public String timeTablePage(Model model, HttpSession httpSession) {
        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        List<ClassTable> classTables = classTableService.findAll();
        //order by SeqNo
        classTables = classTables.stream().sorted(Comparator.comparing(ClassTable::getSeqNo)).collect(Collectors.toList());
        model.addAttribute("classList", classTables);
        return "class_table";
    }

    @GetMapping({"/editTable/{id}"})
    public String editTablePage(@PathVariable("id") long id, Model model) {
        ClassTable classTable = classTableService.findById(id);
        model.addAttribute("classTable",classTable);
        return "class_edit_table";
    }

    @PostMapping({"/editTable"})
    public String editTable(ClassTable classTable) {
        classTableService.updateTable(classTable);
        return "redirect:/classTable";
    }
}
