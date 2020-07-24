package com.ssafy.indieAndBob.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.user.dao.UserDao;
import com.ssafy.indieAndBob.user.dto.Follow;
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

	@Override
	public int registerFollow(Follow follow) {
		// TODO Auto-generated method stub
		return userDao.registerFollow(follow);
	}

	@Override
	public List<String> getFollower(String userId) {
		// TODO Auto-generated method stub
		return userDao.getFollower(userId);
	}

	@Override
	public List<String> getFollowing(String userId) {
		// TODO Auto-generated method stub
		return userDao.getFollowing(userId);
	}

	@Override
	public int deleteFollowing(Follow follow) {
		// TODO Auto-generated method stub
		return userDao.deleteFollowing(follow);
	}

	@Override
	public boolean isFollowing(Follow follow) {
		// TODO Auto-generated method stub
		return userDao.isFollowing(follow);
	}

}
