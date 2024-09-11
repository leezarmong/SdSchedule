package com.sd.schedule.model.member;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.sd.schedule.model.station.StationVO;

public interface MemberService {
	
	//멤버 리스트
	public List<MemberVO>memberList(String user_id);
	
	//멤버 리스트(페이)
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end);
	

	
	//시퀀스 불러오기
	public int getNextMemberSeq();
	
	
	//멤버 스테이션 동시 입력
	void insertMemberAndStation(MemberVO vo, StationVO svo);
	
	// 멤버와 스테이션 한명일 경우
	public void insertOneMemberAndStaiton (MemberVO vo , StationVO svo);
	
	//멤버 삭제
	public void deleteMember (String member_name);
	
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
	
	//엑셀 데이터 추출후 삭제
	public List<String> deleteMembersFromExel(MultipartFile file, String user_id)throws IOException;
	
	//엑셀 데이터 추출후 등록
	public List<String> addMembersFromExel(MultipartFile file, String user_id) throws IOException;
	
	//새로운 신규인원 새로 추가
	public void insertMembers(List<MemberVO> members);

	
	
	

}
