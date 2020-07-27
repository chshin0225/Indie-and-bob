package com.ssafy.indieAndBob.game.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.GameComment;

@Repository
public class GameCommentDaoImpl implements GameCommentDao{
	private static String ns = "com.ssafy.indieAndBob.mapper.gamecomment.";

	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public int registerComment(GameComment comment) {
		return temp.insert(ns+"registerComment", comment);
	}

}
