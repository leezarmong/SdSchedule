package com.sd.schedule.model.memberimpl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sd.schedule.model.member.MemberVO;




@Mapper
public interface MemberMapper {
	
	//멤버 리스트
	public List<MemberVO> memberList (String user_id);
	
	//멤버 리스트(페이지)
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end);
	
	
	//멤버 입력
	public void insertMember (MemberVO vo);
	
	//멤버 삭제
	public void deleteMember (int member_no);
	
	//멤버 수 카운트
	public int countMember (MemberVO vo);
	
	//이름 중복 검사
	//public int nameCount (MemberVO vo);
	
	// Mapper interface에서 해당 메소드 추가
	int countMemberByNameAndUserId(@Param("member_name") String member_name, @Param("user_id") String user_id);
	
	//멤버 수 불러오기(검색)
	public int countSearch(String name, String user_id);
	
	//멤버 검색
	public List<MemberVO> searchMember (String name, int start, int end, String user_id);
	
	//멤버 업데이트
	public void updateMember (MemberVO vo);
	
	//불필요 멤버 전체 삭제
	public void deleteMembers(@Param("list") List<MemberVO> members);
	

	
}
