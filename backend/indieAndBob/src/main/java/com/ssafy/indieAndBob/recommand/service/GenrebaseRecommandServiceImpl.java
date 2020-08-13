package com.ssafy.indieAndBob.recommand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.recommand.dao.GenrebaseRecommandDao;
import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;

@Service
public class GenrebaseRecommandServiceImpl implements GenrebaseRecommandService{
	
	@Autowired
	GenrebaseRecommandDao dao;
	@Override
	public List<GenrebaseRecommand> recommandGenrebase(String nickname){
		return dao.recommandGenrebase(nickname);
	}
}
