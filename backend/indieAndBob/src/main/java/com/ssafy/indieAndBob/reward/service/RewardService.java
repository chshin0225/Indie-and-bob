package com.ssafy.indieAndBob.reward.service;

import java.util.List;

import com.ssafy.indieAndBob.reward.dto.Reward;

public interface RewardService {
	int registerReward(Reward reward);
	Reward selectRewardById(int rewardId);
	List<Reward> selectAllReward();
	List<Reward> selectRewardByGameId(int gameId);
	int updateRewardByRewardId(Reward reward);
	int deleteRewardByRewardId(int rewardId);
	int buyReward(int rewardId);
}
