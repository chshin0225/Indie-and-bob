package com.ssafy.indieAndBob.game.service;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.dto.GameRegister;
import com.ssafy.indieAndBob.user.dto.User;

public interface GameService {

	Game selectGameById(String gameId);
	int registerGame(GameRegister game);
	int updateGame(Game game);
	int deleteGameById(Game game);
	List<Game> selectAllGame(int page);
	int gameLike(GameLike like);
	List<Game> selectGameByNickname(String nickname);
	List<User> selectUserByGameId(String gameId);
	int deleteGameLike(GameLike like);
	GameLike isLike(GameLike like);
}
