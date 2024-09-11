package com.sd.schedule.model.stationimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sd.schedule.model.member.MemberVO;
import com.sd.schedule.model.station.StationVO;



@Mapper
public interface StationMapper {
	
	
	
	//스테이션 삭제
	public void deleteStation (String member_name);
	
	//스테이션 업데이트
	public void updateStation (StationVO svo);
	
	//스테이션 이름 추출
	public List<StationVO> selectStationsByMemberNames(List<String> memberNames);
	
	//불필요 멤버 전체 삭제
	public void deleteStations(@Param("list") List<MemberVO> members);
}
