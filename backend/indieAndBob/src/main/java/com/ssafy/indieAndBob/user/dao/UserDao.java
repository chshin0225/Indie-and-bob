package com.ssafy.indieAndBob.user.dao;

import com.ssafy.indieAndBob.user.dto.User;

public interface UserDao {
	User selectByEmailAndPassword(User user);
}
