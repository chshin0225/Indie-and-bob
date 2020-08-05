package com.ssafy.indieAndBob.reward.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {

	private int rewardId;
	private int gameId;
	private String rewardName;
	private String rImg;
	private int leftCount;
	private int price;
	private String content;
}
