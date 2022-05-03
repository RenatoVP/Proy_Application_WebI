package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SpringBoot")
public class ProyectController {
	
	@GetMapping("/App")
	public String Springboot() {
		return "springboot";
	}
}
