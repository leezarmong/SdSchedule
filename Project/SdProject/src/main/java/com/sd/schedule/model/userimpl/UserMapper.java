package com.sd.schedule.model.userimpl;

import org.apache.ibatis.annotations.Mapper;

import com.sd.schedule.model.user.UserVO;




@Mapper
public interface UserMapper {
	
	
	//로그인 시 확인, 유저 체크 
	public int checkMember(UserVO vo);
	
	//로그인
	public UserVO login(UserVO vo);

}
