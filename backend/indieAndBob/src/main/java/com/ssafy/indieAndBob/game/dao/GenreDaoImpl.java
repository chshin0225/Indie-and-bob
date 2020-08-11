package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.Genre;

@Repository
public class GenreDaoImpl implements GenreDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.genre.";

	@Autowired
	SqlSessionTemplate temp;
	@Override
	public List<Genre> selectAllGenre() {
		return temp.selectList(ns + "selectAllGenre");
	}
	
	@Override
	public List<String> selectGenreNameByGameId(int gameId){
		return temp.selectList(ns + "selectGenreNameByGameId", gameId);
	}

	@Override
	public List<String> selectGenreNameByNickname(String nickname) {
		return temp.selectList(ns + "selectGenreNameByNickname", nickname);
	}
}
