package com.ssafy.indieAndBob.recommand.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;

@Repository
public class UserbaseRecommandDaoImpl implements UserBaseRecommandDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.userbaseRecommand.";
	
	@Autowired
	SqlSessionTemplate temp;
	@Override
	public List<UserbaseRecommand> userbaseRecommand() {
		return temp.selectList(ns + "userbaseRecommand");
	}
	@Override
	public int getUserId(String nickname) {
		return temp.selectOne(ns + "getUserId", nickname);
	}
	@Override
	public List<GameAll> randomRecommend() {
		return temp.selectList(ns + "selectRandGame");
	}
	
}
