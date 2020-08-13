package com.ssafy.indieAndBob.search.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.user.dto.User;

@Repository
public class SearchDaoImpl implements SearchDao {

	private static String ns = "com.ssafy.indieAndBob.mapper.search.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public List<User> userSearch(String keyword) {
		return temp.selectList(ns + "searchUser", keyword);
	}

	@Override
	public List<GameAll> gameSearch(String keyword) {
		return temp.selectList(ns + "searchGame", keyword);
	}

	@Override
	public List<Community> communitySearch(String keyword) {
		return temp.selectList(ns + "searchCommunity", keyword);
	}

}
