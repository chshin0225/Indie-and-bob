package com.ssafy.indieAndBob.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
	private String type;
	private String nickname;
	private String target;
}
