package com.sd.schedule.model.stationimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.schedule.model.station.StationService;
import com.sd.schedule.model.station.StationVO;




@Service
public class StationServiceImpl implements StationService{
	
	@Autowired
	StationMapper stationMapper;
	
	
	// 스테이션 리스트
//	public List<StationVO> stationListByUserId(String user_id) {
//	    return stationMapper.stationListByUserId(user_id);
//	}
	
	//스테이션 입력
	public void insertStation (StationVO svo) {
		if(svo.getMake() == null) svo.setMake("");
		if(svo.getGrill() == null) svo.setGrill("");
		if(svo.getFrei() == null) svo.setFrei("");
		if(svo.getExpo() == null) svo.setExpo("");
		if(svo.getDish() == null) svo.setDish("");
		stationMapper.insertStation(svo);
	}
	
	//스테이션 삭제
	public void deleteStation (String member_name) {
		stationMapper.deleteStation(member_name);
	}
	
	//스테이션 업데이트
	public void updateStation (StationVO svo) {
		if(svo.getMake() == null) svo.setMake("");
		if(svo.getGrill() == null) svo.setGrill("");
		if(svo.getFrei() == null) svo.setFrei("");
		if(svo.getExpo() == null) svo.setExpo("");
		if(svo.getDish() == null) svo.setDish("");
		stationMapper.updateStation(svo);
	}
	
	//스테이션 이름 추출
	public List<StationVO> selectStationsByMemberNames(List<String> memberNames) {

	    return stationMapper.selectStationsByMemberNames(memberNames);
	}



}
