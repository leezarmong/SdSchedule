package com.sd.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.HttpSession;

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
		public String schedule(Model model, HttpSession session) {
			
			 // 세션에서 user_id 가져오기
		    UserVO user = (UserVO) session.getAttribute("user");
			String user_id = user.getUser_id();
			
			
			List<MemberVO> list = memberService.memberList(user_id);
			model.addAttribute("list", list);
			
			return "menu/schedule";
		}

}
