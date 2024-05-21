package com.sd.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	
	// 로그인 페이지 
	@GetMapping("/loginpage")
	public String loginpage() {
		
		return "login/loginpage";
	}
	
	
	
	// 로그인
	@PostMapping("/login")
	public String login(UserVO vo, HttpSession session) {
	    UserVO user = userService.login(vo);

	    if (user != null) {
	        session.setAttribute("user", user);
	        return "index"; // URL을 '/'로 리디렉션하여 홈 페이지로 이동
	    } else {
	        return "login/loginpage";
	    }
	}
	
	
	
	// 로그인 시 확인, 유저 체크
	@ResponseBody
	@PostMapping("/checkMember")
	public int checkMember(UserVO vo) {
		int checkMember = userService.checkMember(vo);
	    return checkMember;
	}
	
	// 로그아웃
	 @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:index"; 
	    }
	
	
	

}
