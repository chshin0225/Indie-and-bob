package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.dto.GameGenre;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.user.dto.User;

@Repository
public class GameDaoImpl implements GameDao{

	private static String ns = "com.ssafy.indieAndBob.mapper.game.";

	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public int registerGame(GameAll game) {
		if(temp.insert(ns + "registerGame", game) != 1) {
			return 0;
		}
		return game.getGameId();
	}
	
	@Override
	public int registerDetail(GameAll detail) {
		return temp.insert(ns + "registerGameDetail", detail);
	}

	@Override
	public GameAll selectGameById(String gameId) {
		return temp.selectOne(ns+ "selectGameById", gameId);
	}

	@Override
	public List<Game> selectAllGame(int page) {
		return temp.selectList(ns + "selectAllGame",page);
	}

	@Override
	public int gameLike(GameLike like) {
		return temp.insert(ns+"gameLike", like);
	}

	@Override
	public List<Game> selectGameByNickname(String nickname) {
		return temp.selectList(ns+"selectGameByNickname",nickname);
	}

	@Override
	public List<User> selectUserByGameId(String gameId) {
		return temp.selectList(ns+"selectUserByGameId",gameId);
	}

	@Override
	public int deleteGameLike(GameLike like) {
		return temp.delete(ns+"deleteGameLike", like);
	}

	@Override
	public GameLike isLike(GameLike like) {
		return temp.selectOne(ns+"isLike", like);
	}

	@Override
	public int updateGame(Game game) {
		return temp.update(ns + "updateGame", game);
	}

	@Override
	public int deleteGameById(int gameId) {
		return temp.delete(ns + "deleteGameById", gameId);
	}

	@Override
	public List<Game> selectAllGameAdmin(int page) {
		return temp.selectList(ns + "selectAllGameAdmin", page);
	}

	@Override
	public int updateApprove(Game game) {
		return temp.update(ns + "updateApprove", game);
	}

	@Override
	public int fundingGame(Funding funding) {
		return temp.update(ns + "fundingGame", funding);
	}

	@Override
	public int insertGameGenre(GameGenre gameGenre) {
		return temp.insert(ns + "insertGameGenre", gameGenre);
	}

	
}
