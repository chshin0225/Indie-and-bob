package com.ssafy.indieAndBob.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameLike {
	private int gameLikeId;
	private String nickname;
	private String gameId;
}
