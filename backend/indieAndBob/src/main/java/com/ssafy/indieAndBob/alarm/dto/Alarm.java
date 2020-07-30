package com.ssafy.indieAndBob.alarm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
	private int alarmId;
	private String type;
	private String nickname;
	private String target;
	private Date createdAt;
}
