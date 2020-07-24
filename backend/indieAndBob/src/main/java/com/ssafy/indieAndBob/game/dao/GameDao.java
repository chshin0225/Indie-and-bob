package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.user.dto.User;

public interface GameDao {

	Game selectGameById(String gameId);
	int registerGame(Game game);
	List<Game> selectAllGame();
	int gameLike(GameLike like);
	List<Game> selectGameByEmail(String email);
	List<User> selectUserByGameId(String gameId);
	int deleteGameLike(GameLike like);
}
