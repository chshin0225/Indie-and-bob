package com.ssafy.indieAndBob.game.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.Game;

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
}
