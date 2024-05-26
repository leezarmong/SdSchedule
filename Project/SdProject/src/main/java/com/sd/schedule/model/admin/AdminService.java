package com.sd.schedule.model.admin;

import java.util.List;

import com.sd.schedule.model.member.MemberVO;

public interface AdminService {
	
	
	
	//관리자 전용 멤버 리스트
	public List<MemberVO> getAdminMemberList(MemberVO vo, int start, int end);
	
	// 관리자 전용 멤버 수 카운트 
	public int adminCountMember (MemberVO vo);
	
	// 관리자 전용 멤버 검색
	public List<MemberVO> adminSearchMember(String name, int start, int end);
	
	// 관리자 전용 멤버 수 불러오기(검색)
	public int adminCountSearch (String name);
	
	// 관리자 전용 멤버 업데이트 
	public void adminUpdateMember (MemberVO vo);

}
