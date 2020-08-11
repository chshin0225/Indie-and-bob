package com.ssafy.indieAndBob.user.service;

import java.util.List;

import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.dto.UserAll;

public interface UserService {
	User selectByEmailAndPassword(User user);
	User selectByEmail(String user);
	UserAll selectByNickname(String nickname);	
	int registerUser(UserAll user);
	int changeUserInfo(UserAll user);
	
	int registerFollow(Follow follow);
	List<User> getFollower(String userId);
	List<User> getFollowing(String userId);
	int deleteFollowing(Follow follow);
	boolean isFollowing(Follow follow);
	List<User> searchUser(String keyword);
}
