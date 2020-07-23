package com.ssafy.indieAndBob.reward.service;

import com.ssafy.indieAndBob.reward.dto.Reward;

public interface RewardService {
	int registerReward(Reward reward);
	Reward selectRewardById(String rewardId);
}
