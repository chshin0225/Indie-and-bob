package com.ssafy.indieAndBob.reward.service;

import java.util.List;

import com.ssafy.indieAndBob.reward.dto.Reward;

public interface RewardService {
	int registerReward(Reward reward);
	Reward selectRewardById(String rewardId);
	List<Reward> selectAllReward();
	List<Reward> selectRewardByGameId(String gameId);
}
