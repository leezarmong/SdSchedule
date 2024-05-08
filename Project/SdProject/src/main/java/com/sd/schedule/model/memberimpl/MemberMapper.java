package com.sd.schedule.model.memberimpl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sd.schedule.model.member.MemberVO;




@Mapper
public interface MemberMapper {
	
	//멤버 리스트
	public List<MemberVO> memberList ();
	
	//멤버 리스트(페이지)
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end);
	
	//멤버 입력
	public void insertMember (MemberVO vo);
	
	//멤버 삭제
	public void deleteMember (int member_no);
	
	//멤버 수 카운트
	public int countMember (MemberVO vo);
	
	//이름 중복 검사
	public int nameCount (MemberVO vo);
	
	//멤버 수 불러오기(검색)
	public int countSearch (String sPrd);
	
	//멤버 검색
	public List<MemberVO> searchMember (String sPrd, int start, int end);
	
	//멤버 업데이트
	public void updateMember (MemberVO vo);
	

}
