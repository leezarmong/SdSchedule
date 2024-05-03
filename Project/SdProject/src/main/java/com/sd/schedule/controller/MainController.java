package com.sd.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;

@Controller
public class MainController {

	@Autowired
	MemberService memberService;

	// 메인화면
	@GetMapping("/")
	public String main() {
		return "index";
	}

	// 스케줄
	@GetMapping("/schedule")
	public String schedule(Model model) {
		
		List<MemberVO> list = memberService.getMemberList();
		model.addAttribute("list", list);
		
		return "menu/schedule";
	}

}
