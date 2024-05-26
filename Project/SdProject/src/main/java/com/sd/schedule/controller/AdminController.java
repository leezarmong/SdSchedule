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
import com.sd.schedule.model.user.UserVO;
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
			

	    	List<MemberVO> list =adminService.getAdminMemberList(vo, start, end);
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("list", list);
	        map.put("count", count);
	        map.put("pager", pager);
	        model.addAttribute("map", map);      
	   
	        
	        return "admin/adminpage";
	    }
	
	
	//admin search page
	public String adminSearchMember() {
		
		return "admin/adminsearchmember";
	}

}
