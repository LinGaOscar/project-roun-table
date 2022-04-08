package com.javaclass.roundtable.controller;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.service.ClassTableServiceImpl;
import com.javaclass.roundtable.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String indexPage(HttpSession httpSession) {

        if (Objects.isNull(httpSession.getAttribute("loginCheck"))) {
            return "/login";
        }
        return "index";
    }

}
