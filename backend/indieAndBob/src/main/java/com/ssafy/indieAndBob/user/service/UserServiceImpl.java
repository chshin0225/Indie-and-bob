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
		return userDao.changeUserInfo(user);
	}

	@Override
	public int registerFollow(Follow follow) {
		return userDao.registerFollow(follow);
	}

	@Override
	public List<User> getFollower(String userId) {
		return userDao.getFollower(userId);
	}

	@Override
	public List<User> getFollowing(String userId) {
		return userDao.getFollowing(userId);
	}

	@Override
	public int deleteFollowing(Follow follow) {
		return userDao.deleteFollowing(follow);
	}

	@Override
	public boolean isFollowing(Follow follow) {
		return userDao.isFollowing(follow);
	}

	@Override
	public User selectByNickname(String nickname) {
		return userDao.selectByNickname(nickname);
	}

	@Override
	public List<User> searchUser(String keyword) {
		return userDao.searchUser(keyword);
	}

}
