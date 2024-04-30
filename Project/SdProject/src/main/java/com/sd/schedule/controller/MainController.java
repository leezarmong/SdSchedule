package com.sd.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	
	//메인화면 
	@GetMapping("/")
	public String main () {
		return "index";
	}
	
	//스케줄
	@GetMapping("/schedule")
	public String schedule () {
		return "menu/schedule";
	}

}
