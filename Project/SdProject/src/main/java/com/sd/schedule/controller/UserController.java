package com.sd.schedule.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
	public String login(UserVO vo, HttpSession session, BindingResult bindingResult, HttpServletResponse response) {
	    UserVO user = userService.login(vo);
	    LocalDateTime now = LocalDateTime.now();
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE HH시 mm분");
	    String formattedDate = now.format(formatter);
	    
	    System.out.println(user.getUser_id() + "님이 " + formattedDate + "에 로그인 했습니다.");
	    
	    if (user != null) {
	        session.setAttribute("user", user);
	        
	        Cookie cookie = new Cookie("userId", String.valueOf(user.getUser_id()));
	        response.addCookie(cookie); // 응답에 쿠키 추가
	        
	        return "index";
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
	    public String logout(HttpSession session, HttpServletResponse response) {
		 expireCookie(response, "userId");
	        session.invalidate();
	        return "redirect:/"; 
	    }
	 private void expireCookie(HttpServletResponse response, String cookieName) {
		 Cookie cookie = new Cookie(cookieName, null);
		 cookie.setMaxAge(0);
		 response.addCookie(cookie);
		}
	
	
	

}
