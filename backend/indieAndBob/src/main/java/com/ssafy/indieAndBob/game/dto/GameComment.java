package com.ssafy.indieAndBob.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameComment {
	private int gcId;
	private String email;
	private int gameId;
	private String content;
	private String createdAt;
}
