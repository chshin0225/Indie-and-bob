package com.ssafy.indieAndBob.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.user.dao.UserDao;
import com.ssafy.indieAndBob.user.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User selectByEmailAndPassword(User user) {
		return userDao.selectByEmailAndPassword(user);
	}

	@Override
	public User selectByEmail(String user) {
		return userDao.selectByEmail(user);
	}

	@Override
	public int registerUser(User user) {
		return userDao.registerUser(user);
	}

	@Override
	public int changeUserInfo(User user) {
		// TODO Auto-generated method stub
		return userDao.changeUserInfo(user);
	}

}
