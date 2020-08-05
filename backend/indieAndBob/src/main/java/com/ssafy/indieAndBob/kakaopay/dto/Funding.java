package com.ssafy.indieAndBob.kakaopay.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funding {
	private int fundingId;
	private String rewardName;
	private int rewardId;
	private String nickname;
	private int money;
	private Date fundedAt;
	
}
