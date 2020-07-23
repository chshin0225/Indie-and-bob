package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;

public interface GameDao {

	Game selectGameById(String gameId);
	int registerGame(Game game);
	List<Game> selectAllGame();
}
