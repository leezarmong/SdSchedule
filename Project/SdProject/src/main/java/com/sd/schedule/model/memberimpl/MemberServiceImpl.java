package com.sd.schedule.model.memberimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.member.MemberService;
import com.sd.schedule.model.member.MemberVO;



@Service
public class MemberServiceImpl implements MemberService {
	
	
	@Autowired
	private MemberMapper memberMapper;
	
	
	//멤버 리스트
	@Override
	public List<MemberVO>getMemberList() throws Exception{
		return memberMapper.getMemberList();
	}

}
