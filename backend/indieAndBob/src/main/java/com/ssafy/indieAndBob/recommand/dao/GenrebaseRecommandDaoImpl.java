package com.ssafy.indieAndBob.recommand.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;

@Repository
public class GenrebaseRecommandDaoImpl implements GenrebaseRecommandDao{
private static String ns = "com.ssafy.indieAndBob.mapper.genrebaseRecommand.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public List<GenrebaseRecommand> recommandGenrebase(String nickname){
		return temp.selectList(ns + "genrebaseRecommand", nickname);
	}

}
