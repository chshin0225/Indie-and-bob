package com.ssafy.indieAndBob.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GameDao;
import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameGenre;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.user.dto.User;

@Service
public class GameServiceImpl implements GameService{

	@Autowired
	GameDao gamedao;
	
	@Override
	public int registerGame(GameAll game) {
		int gameId = gamedao.registerGame(game);
		gamedao.registerDetail(game);
		for(int genre : game.getGenre()) {
			GameGenre gameGenre = new GameGenre(game.getGameId(), genre);
			gamedao.insertGameGenre(gameGenre);
		}
		return gameId;
	}
	
	@Override
	public int updateGame(Game game) {
		return gamedao.updateGame(game);
	}
	
	@Override
	public int deleteGameById(int gameId) {
		return gamedao.deleteGameById(gameId);
	}

	@Override
	public GameAll selectGameById(String gameId) {
		return gamedao.selectGameById(gameId);
	}

	@Override
	public List<Game> selectAllGame(int page) {
		return gamedao.selectAllGame(page);
	}

	@Override
	public int gameLike(GameLike like) {
		return gamedao.gameLike(like);
	}

	@Override
	public List<Game> selectGameByNickname(String nickname) {
		return gamedao.selectGameByNickname(nickname);
	}

	@Override
	public List<User> selectUserByGameId(String gameId) {
		return gamedao.selectUserByGameId(gameId);
	}

	@Override
	public int deleteGameLike(GameLike like) {
		return gamedao.deleteGameLike(like);
	}

	@Override
	public GameLike isLike(GameLike like) {
		return gamedao.isLike(like);
	}

	@Override
	public List<Game> selectAllGameAdmin(int page) {
		return gamedao.selectAllGameAdmin(page);
	}

	@Override
	public int updateApprove(Game game) {
		return gamedao.updateApprove(game);
	}

	@Override
	public int fundingGame(Funding funding) {
		return gamedao.fundingGame(funding);
	}

	
}
