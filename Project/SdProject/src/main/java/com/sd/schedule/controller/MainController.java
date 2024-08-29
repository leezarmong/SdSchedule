package com.sd.schedule.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.model.station.StationService;
import com.sd.schedule.model.station.StationVO;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	StationService stationService;

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
		
		
		// user_id 에 해당하는 이름과 직급 
		List<MemberVO> list = memberService.memberList(user_id);
		
		// memberName list 에서 이름만 가져옴. stream() api 사용. 람다 형식
		List<String> memberName = list.stream().map(MemberVO::getMember_name).collect(Collectors.toList());
		
		// station 등급 select
		List<StationVO> stationList = stationService.selectStationsByMemberNames(memberName); 
	
		
		model.addAttribute("list", list);
		model.addAttribute("stationList",stationList);
		
		System.out.println("Station List: " + stationList);
		
		return "menu/schedule";
	}

}
