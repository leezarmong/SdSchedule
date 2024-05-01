package com.sd.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;



@Controller
public class MemberController {
	
	
	@Autowired
	MemberService memberService;
	
	
	//멤버 리스트
	@GetMapping("/memberpage")
	public String memberpage(Model model)  {
		
		List<MemberVO> list =memberService.getMemberList();
		model.addAttribute("list",list);
		
		return "member/memberpage";
	}
	
	//멤버 추가
	@PostMapping("/insert")
	public String insert(MemberVO vo){
		
		memberService.insertMember(vo);

		return "member/memberpage";
	}

}
