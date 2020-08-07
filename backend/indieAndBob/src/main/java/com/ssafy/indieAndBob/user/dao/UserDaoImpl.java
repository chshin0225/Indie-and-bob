package com.ssafy.indieAndBob.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.dto.UserAll;
import com.ssafy.indieAndBob.user.dto.UserGenre;

@Repository
public class UserDaoImpl implements UserDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.user.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public User selectByEmailAndPassword(User user) {
		return temp.selectOne(ns + "selectByEmailAndPassword",user);
	}

	@Override
	public User selectByEmail(String email) {
		return temp.selectOne(ns+"selectByEmail" , email);
	}

	@Override
	public int registerUser(UserAll user) {
		return temp.insert(ns + "registerUser", user);
	}

	@Override
	public int changeUserInfo(User user) {
		return temp.update(ns + "changeUserInfo", user);
	}

	@Override
	public int registerFollow(Follow follow) {
		return temp.insert(ns + "registerFollow", follow);
	}

	@Override
	public List<User> getFollower(String userId) {
		return temp.selectList(ns + "getFollower", userId);
	}

	@Override
	public List<User> getFollowing(String userId) {
		return temp.selectList(ns + "getFollowing", userId);
	}

	@Override
	public int deleteFollowing(Follow follow) {
		return temp.delete(ns + "deleteFollowing", follow);
	}

	@Override
	public boolean isFollowing(Follow follow) {
		Follow f = temp.selectOne(ns + "isFollowing", follow);
		if(f == null) return false;
		return true;
	}

	@Override
	public User selectByNickname(String nickname) {
		return temp.selectOne(ns + "selectByNickname", nickname);
	}

	@Override
	public List<User> searchUser(String keyword) {
		return temp.selectList(ns + "searchUser", keyword);
	}

	@Override
	public int registerUserGenre(UserGenre userGenre) {
		return temp.insert(ns + "registerUserGenre", userGenre);
	}

}
