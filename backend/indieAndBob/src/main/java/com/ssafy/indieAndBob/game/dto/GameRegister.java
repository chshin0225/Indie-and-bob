package com.ssafy.indieAndBob.game.dto;

import java.util.Date;

public class GameRegister {
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
