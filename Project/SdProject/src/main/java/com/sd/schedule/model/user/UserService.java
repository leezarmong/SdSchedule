package com.sd.schedule.model.user;





public interface UserService {
	
	
	//로그인 시 확인, 유저 체크 
	public int checkMember(UserVO vo);
	
	//로그인
	public UserVO login(UserVO vo);

}
