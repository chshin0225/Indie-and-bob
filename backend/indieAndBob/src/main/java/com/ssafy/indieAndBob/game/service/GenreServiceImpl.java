package com.ssafy.indieAndBob.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GenreDao;
import com.ssafy.indieAndBob.game.dto.Genre;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreDao genreDao;
	
	@Override
	public List<Genre> selectAllGenre() {
		return genreDao.selectAllGenre();
	}

}
