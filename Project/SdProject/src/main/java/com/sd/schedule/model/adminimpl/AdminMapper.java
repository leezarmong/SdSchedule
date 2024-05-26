package com.sd.schedule.model.adminimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sd.schedule.model.member.MemberVO;


@Mapper
public interface AdminMapper {
	
	
	
	//관리자 전용 멤버 리스트
	public List<MemberVO> getAdminMemberList(MemberVO vo, int start, int end);
	
	
	// 관리자 전용 멤버 수 카운트 
	public int adminCountMember (MemberVO vo);

}
