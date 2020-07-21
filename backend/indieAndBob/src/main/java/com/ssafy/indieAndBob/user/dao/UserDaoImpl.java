package com.ssafy.indieAndBob.user.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.user.dto.User;

@Repository
public class UserDaoImpl implements UserDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.user.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public User selectByEmailAndPassword(User user) {
		return temp.selectOne(ns + "selectByEmailAndPassword",user);
	}

}
