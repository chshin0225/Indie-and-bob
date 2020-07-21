package com.ssafy.indieAndBob.user.service;

import com.ssafy.indieAndBob.user.dto.User;

public interface UserService {
	User selectByEmailAndPassword(User user);
}
