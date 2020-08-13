package com.ssafy.indieAndBob.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GameDao;
import com.ssafy.indieAndBob.game.dao.GenreDao;
import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameGenre;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.dto.MyGameSearch;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.dto.GameAllLike;
import com.ssafy.indieAndBob.user.dto.User;

@Service
public class GameServiceImpl implements GameService{

	@Autowired
	GameDao gamedao;
	@Autowired
	GenreDao genredao;
	
	@Override
	public int registerGame(GameAll game, String extension) {
		int gameId = gamedao.registerGame(game, extension);
		gamedao.registerDetail(game);
		for(int genre : game.getGenre()) {
			GameGenre gameGenre = new GameGenre(game.getGameId(), genre);
			gamedao.insertGameGenre(gameGenre);
		}
		return gameId;
	}
	
	@Override
	public int updateGame(GameAll game) {
		gamedao.deleteGameGenre(game.getGameId());
		for(int genre : game.getGenre()) {
			GameGenre gameGenre = new GameGenre(game.getGameId(), genre);
			gamedao.insertGameGenre(gameGenre);
		}
		return gamedao.updateGame(game);
	}
	
	@Override
	public int deleteGameById(int gameId) {
		return gamedao.deleteGameById(gameId);
	}

	@Override
	public GameAll selectGameById(int gameId) {
		GameAll gameAll = gamedao.selectGameById(gameId);
		List<String> genreName = genredao.selectGenreNameByGameId(gameAll.getGameId());
		gameAll.setGenreName(genreName);
		return gameAll;
	}

	@Override
	public List<GameAll> selectAllGame(int page) {
		List<GameAll> games = gamedao.selectAllGame(page);
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public int gameLike(GameLike like) {
		return gamedao.gameLike(like);
	}

	@Override
	public List<GameAll> selectGameByNickname(MyGameSearch search) {
		List<GameAll> games = gamedao.selectGameByNickname(search);
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
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
	public List<GameAll> selectAllGameAdmin(int page) {
		List<GameAll> games = gamedao.selectAllGameAdmin(page);
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public int updateApprove(Game game) {
		return gamedao.updateApprove(game);
	}

	@Override
	public int fundingGame(Funding funding) {
		return gamedao.fundingGame(funding);
	}

	@Override
	public List<GameAll> selectMadeGameByNickname(MyGameSearch search) {
		List<GameAll> games = gamedao.selectMadeGameByNickname(search);
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<GameAllLike> mostLikeGame() {
		List<GameAllLike> games = gamedao.mostLikeGame();
		for(GameAllLike game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<GameAll> deadlineGame() {
		List<GameAll> games = gamedao.deadlineGame();
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<GameAll> mostFundedPriceGame() {
		List<GameAll> games = gamedao.mostFundedPriceGame();
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<GameAll> mostFundedPercentGame() {
		List<GameAll> games = gamedao.mostFundedPercentGame();
		for(GameAll game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public int gameSubmit(int gameId) {
		return gamedao.gameSubmit(gameId);
	}

	
}
