package com.ssafy.indieAndBob.reward.service;

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
		// TODO Auto-generated method stub
		return rewarddao.selectRewardById(rewardId);
	}

	
}
