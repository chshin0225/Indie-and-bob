package com.ssafy.indieAndBob.game.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundingGame {
	private int gameId;
	private String name;
	private Date deadline;
	private int aim;
	private int leftPrice;
	private boolean isEnd;
	private int isApprove;
	private String nickname;
	private String thumbnail;
	private Date createdAt;
	private Date updatedAt;
	private Date startedAt;
    private List<Integer> genre;
    private List<String> genreName;
    private int rewardId;
	private String rewardName;
	private String rImg;
	private int leftCount;
	private int price;
	private String content;
	private String profile;
	private int leftDay;
}
