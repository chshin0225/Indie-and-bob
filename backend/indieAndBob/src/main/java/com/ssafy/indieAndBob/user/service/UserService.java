package com.ssafy.indieAndBob.user.service;

import java.util.List;

import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;

public interface UserService {
	User selectByEmailAndPassword(User user);
	User selectByEmail(String user);
	int registerUser(User user);
	int changeUserInfo(User user);
	
	int registerFollow(Follow follow);
	List<String> getFollower(String userId);
	List<String> getFollowing(String userId);
	int deleteFollowing(Follow follow);
	boolean isFollowing(Follow follow);
}
