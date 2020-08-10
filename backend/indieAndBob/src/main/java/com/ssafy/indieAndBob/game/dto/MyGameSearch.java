package com.ssafy.indieAndBob.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyGameSearch {
	private String nickname;
	private int page;
}
