package com.ssafy.indieAndBob.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.game.dao.GenreDao;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.search.dao.SearchDao;
import com.ssafy.indieAndBob.user.dto.User;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	SearchDao searchDao;
	
	@Autowired
	GenreDao genreDao;
	
	@Override
	public List<User> userSearch(String keyword) {
		return searchDao.userSearch(keyword);
	}

	@Override
	public List<GameAll> gameSearch(String keyword) {
		List<GameAll> games = searchDao.gameSearch(keyword);
		for(GameAll game : games) {
			List<String> genreName = genreDao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<Community> communitySearch(String keyword) {
		return searchDao.communitySearch(keyword);
	}

}
