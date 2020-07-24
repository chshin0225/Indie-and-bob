package com.ssafy.indieAndBob.reward.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {

	public String rewardId;
	public String gameId;
	public String rewardName;
	public String rImg;
	public int leftCount;
	public int price;
	public String content;
}
