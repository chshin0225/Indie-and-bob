package com.ssafy.indieAndBob.game.dao;

import com.ssafy.indieAndBob.game.dto.Game;

public interface GameDao {

	Game selectGameById(String gameId);
	int registerGame(Game game);
}
