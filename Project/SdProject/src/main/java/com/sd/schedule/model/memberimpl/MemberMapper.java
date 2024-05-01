package com.sd.schedule.model.memberimpl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sd.schedule.model.member.MemberVO;




@Mapper
public interface MemberMapper {
	
	//멤버 리스트
	public List<MemberVO>getMemberList() throws Exception;

}
