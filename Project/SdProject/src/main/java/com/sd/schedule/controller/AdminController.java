package com.sd.schedule.controller;

import java.util.HashMap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sd.schedule.model.admin.AdminService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.pager.Pager;

import jakarta.servlet.http.HttpSession;




@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	
	//admin 페이지
	@GetMapping("/adminpage")
	 public String adminPage(@RequestParam(defaultValue = "1") int curPage, MemberVO vo, Model model, HttpSession session) {
			
			
			int count =adminService.adminCountMember(vo);
	        Pager pager = new Pager(count, curPage);
	        int start = pager.getPageBegin();
	        int end = pager.getPageEnd();
	        
	        
	        model.addAttribute("count", count);
			

	    	List<MemberVO> list =adminService.getAdminMemberList(vo, start, end);
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("list", list);
	        map.put("count", count);
	        map.put("pager", pager);
	        model.addAttribute("map", map);      
	   
	        
	        return "admin/adminpage";
	    }
	
	
	//admin search page
	@GetMapping("/adminSearchMember")
	public String adminsearchmember(Model model, HttpSession session, String name, @RequestParam(defaultValue = "1") int curPage) {
		
		
	
		int count = adminService.adminCountSearch(name);
//		System.out.print(count);
	       
	        Pager pager = new Pager(count, curPage);
	        int start = pager.getPageBegin();
	        int end = pager.getPageEnd();
	        
	        session.setAttribute("name",name);	// 아이디 검색
	        session.setAttribute("curPage", curPage);
	        
	      
	        
	        List<MemberVO> list = adminService.adminSearchMember(name, start, end);
//	        System.out.print(user_id);
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("list", list);
	        map.put("count", count);
	        map.put("pager", pager);
	        map.put("name", name);
	        model.addAttribute("map", map);
	        
	        return "admin/adminsearchmember";
	    }
	
	//관리자 전용 멤버 업데이트
	@GetMapping("/adminUpdateMember")
	public String adminUpdateMember (MemberVO vo) {
		
		adminService.adminUpdateMember(vo);
		return  "redirect:adminpage";
	}
	
	//멤버 추가
	@PostMapping("/adminInsertMember")
	public String adminInsertMember(MemberVO vo){
		
		adminService.adminInsertMember(vo);
		return "redirect:adminpage";
	}

}
