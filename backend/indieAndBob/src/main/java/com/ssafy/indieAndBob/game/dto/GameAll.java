package com.ssafy.indieAndBob.game.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameAll {
	public int gameId;
	public String name;
	public Date deadline;
	public int aim;
	public int leftPrice;
	public boolean isEnd;
	public boolean isApprove;
	public String email;
	public String thumbnail;
	public Date createdAt;
	public Date updatedAt;
	public Date startedAt;
    private String content;
}
