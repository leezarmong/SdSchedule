package com.sd.schedule.model.stationimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sd.schedule.model.station.StationVO;



@Mapper
public interface StationMapper {
	
	
	// 스테이션 리스트
//		public List<StationVO> stationListByUserId(String user_id);
	
	//스테이션 입력
	public void insertStation (StationVO svo);
	
	//스테이션 삭제
	public void deleteStation (String member_name);
	
	//스테이션 업데이트
	public void updateStation (StationVO svo);
	
	//스테이션 이름 추출
	public List<StationVO> selectStationsByMemberNames(List<String> memberNames);
}
