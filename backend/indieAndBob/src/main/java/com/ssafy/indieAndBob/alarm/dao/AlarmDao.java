package com.ssafy.indieAndBob.alarm.dao;

import java.util.List;

import com.ssafy.indieAndBob.alarm.dto.Alarm;

public interface AlarmDao {
	int insertAlarm(Alarm alarm);
	List<Alarm> getAlarms(String nickname);
}
