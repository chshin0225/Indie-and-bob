package com.ssafy.indieAndBob.alarm.service;

import java.util.List;

import com.ssafy.indieAndBob.alarm.dto.Alarm;

public interface AlarmService {
	int insertAlarm(Alarm alarm);
	List<Alarm> getAlarms(String nickname);
	String test();
}
