package com.ssafy.indieAndBob.recommand.dao;

import java.util.List;

import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;

public interface GenrebaseRecommandDao {
	public List<GenrebaseRecommand> recommandGenrebase(String nickname);
}
