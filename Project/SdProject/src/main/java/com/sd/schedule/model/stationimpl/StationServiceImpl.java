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

}
