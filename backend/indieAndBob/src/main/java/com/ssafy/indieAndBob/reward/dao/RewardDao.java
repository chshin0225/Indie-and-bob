package com.ssafy.indieAndBob.reward.dao;

import java.util.List;

import com.ssafy.indieAndBob.reward.dto.Reward;

public interface RewardDao {
	int registerReward(Reward reward);
	Reward selectRewardById(String rewardId);
	List<Reward> selectAllReward();
	List<Reward> selectRewardByGameId(String gameId);
	int updateRewardByRewardId(Reward reward);
	int deleteRewardByRewardId(int rewardId);
}
