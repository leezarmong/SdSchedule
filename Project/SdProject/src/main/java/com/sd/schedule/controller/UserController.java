package com.sd.schedule.controller;

import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	
	
	
	@Autowired
	UserService userService;
	
	
	//IP List
	 private List<LoginRecord> loginRecords = new ArrayList<>();
	 
	 
	 
	 public class LoginRecord{
		 
		 private String name;
		 private String date;
		 
		 public LoginRecord(String name , String date) {
			 this.name=name;
			 this.date=date;
		 }
		 
		 public String getName() {
			 return name;
		 }
		 
		 public String getDate() {
			 return date;
		 }
		 
	 }
	

	// 로그인 페이지
	@GetMapping("/loginpage")
	public String loginpage() {

		return "login/loginpage";
	}

	// 로그인
	@PostMapping("/login")
	public String login(UserVO vo, HttpSession session, BindingResult bindingResult, HttpServletResponse response,
			HttpServletRequest request, Model model) {
		UserVO user = userService.login(vo);
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE HH시 mm분");
		String formattedDate = now.format(formatter);

		// client IP 가져오기
		String clientIP = userService.getRemoteIP(request);
//		System.out.println(user.getUser_id() + "님이 " + formattedDate + "에 로그인 했습니다. IP >> " + clientIP);
		
		// client IP 담기
		String name = userService.getNameFromIP(clientIP);
		
		LoginRecord record = new LoginRecord(name, formattedDate);
		loginRecords.add(record);
		 model.addAttribute("loginRecords", loginRecords);
		
		

		if (user != null) {
			session.setAttribute("user", user);

			// 브라우저 닫을시 로그아웃
			Cookie cookie = new Cookie("userId", String.valueOf(user.getUser_id()));
			response.addCookie(cookie); // 응답에 쿠키 추가

			return "index";
		} else {
			return "login/loginpage";
		}
	}
	
	
	@PostMapping("/clear")
	 public String clearIpList() {
        loginRecords.clear();
        return "redirect:adminpage";
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
