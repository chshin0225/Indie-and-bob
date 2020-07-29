package com.ssafy.indieAndBob.game.service;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.user.dto.User;

public interface GameService {

	GameAll selectGameById(String gameId);
	int registerGame(GameAll game);
	int updateGame(Game game);
	int deleteGameById(int gameId);
	List<Game> selectAllGame(int page);
	List<Game> selectAllGameAdmin(int page);
	int gameLike(GameLike like);
	List<Game> selectGameByNickname(String nickname);
	List<User> selectUserByGameId(String gameId);
	int deleteGameLike(GameLike like);
	GameLike isLike(GameLike like);
	int updateApprove(Game game);
}
