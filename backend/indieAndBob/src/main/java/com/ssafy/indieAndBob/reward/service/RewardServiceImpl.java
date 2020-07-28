package com.ssafy.indieAndBob.reward.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.reward.dao.RewardDao;
import com.ssafy.indieAndBob.reward.dto.Reward;

@Service
public class RewardServiceImpl implements RewardService{

	@Autowired
	RewardDao rewarddao;
	
	@Override
	public int registerReward(Reward reward) {
		
		return rewarddao.registerReward(reward);
	}

	@Override
	public Reward selectRewardById(String rewardId) {
		return rewarddao.selectRewardById(rewardId);
	}

	@Override
	public List<Reward> selectAllReward() {
		return rewarddao.selectAllReward();
	}

	@Override
	public List<Reward> selectRewardByGameId(String gameId) {
		return rewarddao.selectRewardByGameId(gameId);
	}

	@Override
	public int updateRewardByRewardId(Reward reward) {
		return rewarddao.updateRewardByRewardId(reward);
	}

	@Override
	public int deleteRewardByRewardId(int rewardId) {
		return rewarddao.deleteRewardByRewardId(rewardId);
	}

	
}
