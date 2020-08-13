package com.ssafy.indieAndBob.recommand.service;

import java.util.List;

import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;

public interface GenrebaseRecommandService {
	public List<GenrebaseRecommand> recommandGenrebase(String nickname);
}
