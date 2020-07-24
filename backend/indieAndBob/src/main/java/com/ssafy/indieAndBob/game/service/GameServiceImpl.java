package com.ssafy.indieAndBob.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GameDao;
import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.user.dto.User;

@Service
public class GameServiceImpl implements GameService{

	@Autowired
	GameDao gamedao;
	
	@Override
	public int registerGame(Game game) {
		
		return gamedao.registerGame(game);
	}

	@Override
	public Game selectGameById(String gameId) {
		return gamedao.selectGameById(gameId);
	}

	@Override
	public List<Game> selectAllGame() {
		return gamedao.selectAllGame();
	}

	@Override
	public int gameLike(GameLike like) {
		return gamedao.gameLike(like);
	}

	@Override
	public List<Game> selectGameByEmail(String email) {
		return gamedao.selectGameByEmail(email);
	}

	@Override
	public List<User> selectUserByGameId(String gameId) {
		return gamedao.selectUserByGameId(gameId);
	}

	@Override
	public int deleteGameLike(GameLike like) {
		return gamedao.deleteGameLike(like);
	}

	
}
