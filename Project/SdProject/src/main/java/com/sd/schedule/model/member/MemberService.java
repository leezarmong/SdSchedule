package com.sd.schedule.model.member;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
	
	//멤버 리스트
	public List<MemberVO>memberList(String user_id);
	
	//멤버 리스트(페이)
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end);
	
	
	//멤버 입력
	public void insertMember (MemberVO vo);
	
	//멤버 삭제
	public void deleteMember (int member_no);
	
	//멤버 수 카운트
	public int countMember (MemberVO vo);
	
	// Mapper interface에서 해당 메소드 추가
	int countMemberByNameAndUserId(@Param("member_name") String member_name, @Param("user_id") String user_id);
	
	//멤버 수 불러오기(검색)
	public int countSearch(String name, String user_id);
		
	//멤버 검색
	public List<MemberVO> searchMember (String name, int start, int end, String user_id);
	
	//멤버 업데이트
	public void updateMember (MemberVO vo);
	
	//엑셀 데이터 추출
	public List<String> deleteMembersFromExel(MultipartFile file, String user_id)throws IOException;
	
	
	

}
