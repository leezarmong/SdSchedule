package com.sd.schedule.model.userimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.user.UserService;
import com.sd.schedule.model.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	// 로그인 시 확인, 유저 체크
	@Override
	public int checkMember(UserVO vo) {
		return userMapper.checkMember(vo);
	}

	// 로그인
	@Override
	public UserVO login(UserVO vo) {
		return (UserVO) userMapper.login(vo);
	}

	// 아이피 불러오기
	public String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		// proxy 환경일 경우
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		// 웹로직 서버일 경우
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	
	//IP addr
	public String getNameFromIP(String ip) {
        switch (ip) {
            case "127.0.0.1":
                return "Jaewon";
            case "220.117.84.163":
                return "Jaewon";
            case "211.117.64.143":
                return "COEX";
            default:
                return ip; // Return the IP address if it doesn't match any predefined cases
        }
    }
	
	
}
