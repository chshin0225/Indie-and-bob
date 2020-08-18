package com.ssafy.indieAndBob.recommand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GenreDao;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.recommand.dao.GenrebaseRecommandDao;
import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;

@Service
public class GenrebaseRecommandServiceImpl implements GenrebaseRecommandService{
	
	@Autowired
	GenrebaseRecommandDao dao;
	
	@Autowired
	GenreDao genredao;
	
	@Override
	public List<GenrebaseRecommand> recommandGenrebase(String nickname){
		List<GenrebaseRecommand> games = dao.recommandGenrebase(nickname);
		for(GenrebaseRecommand game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}
}
