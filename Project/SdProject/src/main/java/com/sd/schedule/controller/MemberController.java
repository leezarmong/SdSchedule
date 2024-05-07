package com.sd.schedule.controller;

import java.util.HashMap;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.pager.Pager;

import jakarta.servlet.http.HttpSession;



@Controller
public class MemberController {
	
	
	@Autowired
	MemberService memberService;
	
	
	//멤버 리스트
	@GetMapping("/memberpage")
    public String memberpage(@RequestParam(defaultValue = "1") int curPage, MemberVO vo, Model model) {
		int count =memberService.countMember(vo);
        Pager pager = new Pager(count, curPage);
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();

    	List<MemberVO> list =memberService.getMemberList(vo, start, end);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("count", count);
        map.put("pager", pager);
        model.addAttribute("map", map);
        return "member/memberpage";
    }
	
	//멤버 추가
	@PostMapping("/insert")
	public String insert(MemberVO vo){
		
		memberService.insertMember(vo);

		return "redirect:memberpage";
	}
	
	//멤버 중복 확인
//	@ResponseBody
//	@PostMapping("/check")
//	public int checkID(MemberVO vo) {
//		int result = memberService.countMember(vo);
//		return result;
//	}
//	
	
	//멤버 삭제
	@PostMapping("/delete")
	public String delete(@RequestParam("member_no") int member_no) {
		
		memberService.deleteMember(member_no);
		return "redirect:memberpage";
	}
	
	//멤버 검색 후 페이지
	@GetMapping("/searchMember")
	public String searchMember(Model model, HttpSession session, String sPrd, @RequestParam(defaultValue = "1") int curPage) {
		int count = memberService.countSearch(sPrd);
	       
	        Pager pager = new Pager(count, curPage);
	        int start = pager.getPageBegin();
	        int end = pager.getPageEnd();
	        
	        session.setAttribute("sPrd",sPrd);	// 아이디 검색
	        session.setAttribute("curPage", curPage);

	        List<MemberVO> list = memberService.searchMember(sPrd, start, end);
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        map.put("list", list);
	        map.put("count", count);
	        map.put("pager", pager);
	        map.put("sPrd", sPrd);
	        model.addAttribute("map", map);
	        
	    	return "member/searchmember";
	    }

}
