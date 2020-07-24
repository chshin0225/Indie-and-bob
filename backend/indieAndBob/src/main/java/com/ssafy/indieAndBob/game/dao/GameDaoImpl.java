package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.user.dto.User;

@Repository
public class GameDaoImpl implements GameDao{

	private static String ns = "com.ssafy.indieAndBob.mapper.game.";

	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public int registerGame(Game game) {
		return temp.insert(ns + "registerGame", game);
	}

	@Override
	public Game selectGameById(String gameId) {
		return temp.selectOne(ns+ "selectGameById", gameId);
	}

	@Override
	public List<Game> selectAllGame() {
		return temp.selectList(ns + "selectAllGame");
	}

	@Override
	public int gameLike(GameLike like) {
		return temp.insert(ns+"gameLike", like);
	}

	@Override
	public List<Game> selectGameByEmail(String email) {
		return temp.selectList(ns+"selectGameByEmail",email);
	}

	@Override
	public List<User> selectUserByGameId(String gameId) {
		return temp.selectList(ns+"selectUserByGameId",gameId);
	}

	@Override
	public int deleteGameLike(GameLike like) {
		return temp.delete(ns+"deleteGameLike", like);
	}
}
