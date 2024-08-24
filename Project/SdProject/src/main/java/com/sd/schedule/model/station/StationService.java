package com.sd.schedule.model.station;

import java.util.List;




public interface StationService {
	
	
	// 스테이션 리스트
//	public List<StationVO> stationListByUserId(String user_id);
	
	//스테이션 입력
	public void insertStation (StationVO svo);
	
	//스테이션 삭제
	public void deleteStation (String member_name);
	
	//스테이션 업데이트
	public void updateStation (StationVO svo);

}
