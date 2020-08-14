package com.ssafy.indieAndBob.game.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
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
	private String reason;
}
