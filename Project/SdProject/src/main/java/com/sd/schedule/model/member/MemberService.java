package com.sd.schedule.model.member;

import java.util.List;

public interface MemberService {
	
	//멤버 리스트
	public List<MemberVO>getMemberList();
	
	//멤버 입력
	public void insertMember(MemberVO vo) ;

}
