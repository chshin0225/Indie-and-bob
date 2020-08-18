package com.ssafy.indieAndBob.recommand.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;

public interface UserBaseRecommandDao {
	List<UserbaseRecommand> userbaseRecommand();
	int getUserId(String nickname);
	List<GameAll> randomRecommend();
}
