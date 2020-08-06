package com.ssafy.indieAndBob.qna.dto;

import com.ssafy.indieAndBob.user.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaVO {
	private int quaId;
	private int gameId;
	private String nickname;
	private String title;
	private String content;
	private boolean isSecret;
	private String answer;
}