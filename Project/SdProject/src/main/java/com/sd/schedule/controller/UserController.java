package com.sd.schedule.controller;

import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.sd.schedule.model.admin.AdminService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;
import com.sd.schedule.pager.Pager;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	//IP List
	private List<LoginRecord> loginRecords = new ArrayList<>();
	
	//IPaddr Class 
	public class LoginRecord {
	    private String name;
	    private String date;
	    private String user_id;
	    
	    public LoginRecord(String name, String date, String user_id) {
	        this.name = name;
	        this.date = date;
	        this.user_id = user_id;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public String getDate() {
	        return date;
	    }
	    
	    public String getUser_id() {
	    	return user_id;
	    }
	    
	    @Override
		public String toString() {
			return "LoginRecord [name=" + name + ", date=" + date + ", user_id=" + user_id + ", getName()=" + getName();
		}
	}
	

	// 로그인 페이지
	@GetMapping("/loginpage")
	public String loginpage() {

		return "login/loginpage";
	}

	// 로그인
	@PostMapping("/login")
	public String login(UserVO vo, HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model) {
	    UserVO user = userService.login(vo);
	    String user_id = user.getUser_id();
	    
	    LocalDateTime now = LocalDateTime.now();

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE HH시 mm분");
	    String formattedDate = now.format(formatter);

	    // client IP 가져오기
	    String clientIP = userService.getRemoteIP(request);
	    
	    // client IP 담기
	    String name = userService.getNameFromIP(clientIP);
	    
	    LoginRecord record = new LoginRecord(name, formattedDate, user_id);
	    loginRecords.add(record);
	    
	    // ip 콘솔 출력
//	    System.out.println("Login Records: " + loginRecords);

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

	
	//admin page
	@GetMapping("/adminpage")
	public String adminPage(@RequestParam(defaultValue = "1") int curPage, MemberVO vo, Model model, HttpSession session, HttpServletRequest request) {
	    int count = adminService.adminCountMember(vo);
	    Pager pager = new Pager(count, curPage);
	    int start = pager.getPageBegin();
	    int end = pager.getPageEnd();
	    
	    model.addAttribute("count", count);
	    
	    // 로그인 기록을 모델에 추가
	    model.addAttribute("loginRecords", loginRecords);
	    
	    List<MemberVO> list = adminService.getAdminMemberList(vo, start, end);
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("list", list);
	    map.put("count", count);
	    map.put("pager", pager);
	    model.addAttribute("map", map);
	    
	    return "admin/adminpage";
	}

	//Login IP Addresses clear 
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
