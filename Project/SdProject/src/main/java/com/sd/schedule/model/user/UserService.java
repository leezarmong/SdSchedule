package com.sd.schedule.model.user;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	
	//로그인 시 확인, 유저 체크 
	public int checkMember(UserVO vo);
	
	//로그인
	public UserVO login(UserVO vo);
	
	// 아이피 불러오기
	public String getRemoteIP(HttpServletRequest request);
	
	//IP addr
	public String getNameFromIP(String ip);

}
