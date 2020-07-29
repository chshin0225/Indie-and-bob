package com.ssafy.indieAndBob.user.dao;

import java.util.List;

import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;

public interface UserDao {
	User selectByEmailAndPassword(User user);
	User selectByEmail(String email);
	User selectByNickname(String nickname);
	List<User> searchUser(String keyword);
	int registerUser(User user);
	int changeUserInfo(User user);
	
	int registerFollow(Follow follow);
	List<User> getFollower(String userId);
	List<User> getFollowing(String userId);
	int deleteFollowing(Follow follow);
	boolean isFollowing(Follow follow);
}
