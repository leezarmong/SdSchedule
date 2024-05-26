package com.sd.schedule.model.adminimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.admin.AdminService;
import com.sd.schedule.model.member.MemberVO;

@Service
public class AdminServiceImpl implements AdminService{
	
	
	@Autowired
	private AdminMapper adminMapper;
	
	
	//관리자 전용 멤버 리스트
	@Override
	public List<MemberVO> getAdminMemberList(MemberVO vo, int start, int end){	
		return adminMapper.getAdminMemberList(vo, start, end);
	}
	
	
	// 관리자 전용 멤버 수 카운트
	@Override
	public int adminCountMember (MemberVO vo) {
		return adminMapper.adminCountMember(vo);
	}
	
	
	// 관리자 전용 멤버 검색
	@Override
	public List<MemberVO> adminSearchMember(String name, int start, int end){
		return adminMapper.adminSearchMember(name, start, end);
	}
	
	// 관리자 전용 멤버 수 불러오기(검색)
	@Override
	public int adminCountSearch (String name) {
		return adminMapper.adminCountSearch(name);
	}

}
