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

	// 멤버 리스트
	@Override
	public List<MemberVO> getMemberList() {
		return memberMapper.getMemberList();
	}

	// 멤버 입력
	public void insertMember(MemberVO vo) {
		memberMapper.insertMember(vo);
		System.out.print(vo);
	}

}
