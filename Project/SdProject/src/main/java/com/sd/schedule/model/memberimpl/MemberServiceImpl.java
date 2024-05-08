package com.sd.schedule.model.memberimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	
	//멤버 리스트
	@Override
	public List<MemberVO> memberList (){
		return memberMapper.memberList();
	}
	
	//멤버 리스트(페이지)
	@Override
	public List<MemberVO> getMemberList(MemberVO vo, int start, int end){
		return memberMapper.getMemberList(vo, start, end);
	}

	//멤버 입력
	@Override
	public void insertMember(MemberVO vo) {
		memberMapper.insertMember(vo);
	}
	
	//멤버 삭제
	@Override
	public void deleteMember(int member_no) {
		memberMapper.deleteMember(member_no);
	}
	
	//멤버 수 카운트
	@Override
	public int countMember (MemberVO vo) {
		return memberMapper.countMember(vo);
	}
	
	//이름 중복 검사
	@Override
	public int nameCount (MemberVO vo) {
		return memberMapper.nameCount(vo);
		}
	
	//멤버 수 불러오기(검색)
	@Override
	public int countSearch (String sPrd) {
		return memberMapper.countSearch(sPrd);
	}
	
	//멤버 검색
	@Override
	public List<MemberVO> searchMember (String sPrd, int start, int end){
		return memberMapper.searchMember(sPrd, start, end);
	}
	
	//멤버 업데이트
	@Override
	public void updateMember (MemberVO vo) {
		memberMapper.updateMember(vo);
	}

}
