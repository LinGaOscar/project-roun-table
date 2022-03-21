package com.javaclass.roundtable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class LoginController {

	@GetMapping("/login")
	public String indexPage() {
		return "login";
	}
	@PostMapping("/login")
	public String userLogin() {
		return null;
		
	}
}
