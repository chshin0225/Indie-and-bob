package com.ssafy.indieAndBob.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDetail {
	private int gameId;
    private String content;
}
