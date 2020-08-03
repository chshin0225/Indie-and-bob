package com.ssafy.indieAndBob.alarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.alarm.dao.AlarmDao;
import com.ssafy.indieAndBob.alarm.dao.AlarmDaoImpl;
import com.ssafy.indieAndBob.alarm.dto.Alarm;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmDao alarmDao;
	
	@Override
	public int insertAlarm(Alarm alarm) {
		return alarmDao.insertAlarm(alarm);
	}

	@Override
	public List<Alarm> getAlarms(String nickname) {
		return alarmDao.getAlarms(nickname);
	}

	@Override
	public String test() {
		return "test service";
	}
	
}
