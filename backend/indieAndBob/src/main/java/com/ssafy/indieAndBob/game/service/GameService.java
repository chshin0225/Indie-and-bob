package com.ssafy.indieAndBob.game.service;

import com.ssafy.indieAndBob.game.dto.Game;

public interface GameService {

	Game selectGameById(String gameId);
	int registerGame(Game game);
}
