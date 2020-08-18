package com.ssafy.indieAndBob.recommand.service;

import java.util.List;

import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;

public interface UserbaseRecommandService {
	List<UserbaseRecommand> userbaseRecommand();
	int getUserId(String nickname);
}
