package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.Genre;

public interface GenreDao {
	List<Genre> selectAllGenre();
}
