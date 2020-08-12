package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameGenre;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.dto.MyGameSearch;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.dto.GameAllLike;
import com.ssafy.indieAndBob.user.dto.User;

public interface GameDao {

	GameAll selectGameById(int gameId);
	int registerGame(GameAll game, String extension);
	int registerDetail(GameAll detail);
	int updateGame(GameAll game);
	int deleteGameById(int gameId);
	List<GameAll> selectAllGame(int page);
	List<Game> selectAllGameAdmin(int page);
	int gameLike(GameLike like);
	List<GameAll> selectGameByNickname(MyGameSearch search);
	List<User> selectUserByGameId(String gameId);
	int deleteGameLike(GameLike like);
	GameLike isLike(GameLike like);
	int updateApprove(Game game);
	int fundingGame(Funding funding);
	int insertGameGenre(GameGenre gameGenre);
	List<GameAll> selectMadeGameByNickname(MyGameSearch search);
	int deleteGameGenre(int gameId);
	List<GameAllLike> mostLikeGame();
	List<GameAll> deadlineGame();
}
