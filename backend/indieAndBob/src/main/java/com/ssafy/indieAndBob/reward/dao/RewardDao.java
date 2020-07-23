package com.ssafy.indieAndBob.reward.dao;

import com.ssafy.indieAndBob.reward.dto.Reward;

public interface RewardDao {
	int registerReward(Reward reward);
	Reward selectRewardById(String rewardId);
}
