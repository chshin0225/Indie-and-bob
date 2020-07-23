package com.ssafy.indieAndBob.game.service;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;

public interface GameService {

	Game selectGameById(String gameId);
	int registerGame(Game game);
	List<Game> selectAllGame();
}
