package com.sd.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;



@Controller
public class MemberController {
	
	
	@Autowired
	MemberService memberService;
	
	
	@GetMapping("/memberpage")
	public String memberpage(Model model) throws Exception {
		
		List<MemberVO> list =memberService.getMemberList();
		model.addAttribute("list",list);
		  //model.addAttribute("data","hello World!");
		
		return "member/memberpage";
	}

}
