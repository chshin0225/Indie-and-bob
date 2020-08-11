package com.ssafy.indieAndBob.user.dao;

import java.util.List;

import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.dto.UserAll;
import com.ssafy.indieAndBob.user.dto.UserGenre;

public interface UserDao {
	User selectByEmailAndPassword(User user);
	User selectByEmail(String email);
	UserAll selectByNickname(String nickname);
	List<User> searchUser(String keyword);
	int registerUser(UserAll user);
	int registerUserGenre(UserGenre userGenre);
	int changeUserInfo(UserAll user);
	
	int registerFollow(Follow follow);
	List<User> getFollower(String userId);
	List<User> getFollowing(String userId);
	int deleteFollowing(Follow follow);
	boolean isFollowing(Follow follow);
	int deleteUserGenre(String nickname);
}
