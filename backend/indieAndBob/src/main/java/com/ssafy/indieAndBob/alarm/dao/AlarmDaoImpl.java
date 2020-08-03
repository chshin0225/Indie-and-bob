package com.ssafy.indieAndBob.alarm.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.alarm.dto.Alarm;

@Repository
public class AlarmDaoImpl implements AlarmDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.alarm.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public int insertAlarm(Alarm alarm) {
		return temp.insert(ns + "insertAlarm", alarm);
	}

	@Override
	public List<Alarm> getAlarms(String nickname) {
		return temp.selectList(ns + "getAlarm", nickname);
	}

}
