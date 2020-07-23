package com.ssafy.indieAndBob.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GameDao;
import com.ssafy.indieAndBob.game.dto.Game;

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

	
}
