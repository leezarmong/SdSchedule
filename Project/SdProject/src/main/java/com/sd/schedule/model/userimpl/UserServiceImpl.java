package com.sd.schedule.model.userimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private UserMapper userMapper;
	
	
		//로그인 시 확인, 유저 체크 
		public int checkMember(UserVO vo) {
			return userMapper.checkMember(vo);
		}
		
		//로그인
		public UserVO login(UserVO vo) {
			return (UserVO) userMapper.login(vo);
		}
}
