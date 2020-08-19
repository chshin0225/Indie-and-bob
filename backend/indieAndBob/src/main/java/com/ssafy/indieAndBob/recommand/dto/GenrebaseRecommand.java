package com.ssafy.indieAndBob.recommand.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenrebaseRecommand {	
	
	private int gameId;
	private String name;
	private Date deadline;
	private int aim;
	private int leftPrice;
	private boolean isEnd;
	private int isApprove;	
	private String thumbnail;
	private Date createdAt;
	private Date updatedAt;
	private Date startedAt;
	private String nickname;
	private String profile;
	private int leftDay;
	private List<String> genreName;
}
