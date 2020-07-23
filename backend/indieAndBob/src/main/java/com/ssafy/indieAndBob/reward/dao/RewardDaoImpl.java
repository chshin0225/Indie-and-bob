package com.ssafy.indieAndBob.reward.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.reward.dto.Reward;

@Repository
public class RewardDaoImpl implements RewardDao{
	private static String ns = "com.ssafy.indieAndBob.mapper.reward.";

	@Autowired
	SqlSessionTemplate temp;

	@Override
	public int registerReward(Reward reward) {
		return temp.insert(ns+"registerReward",reward);
	}

	@Override
	public Reward selectRewardById(String rewardId) {
		
		return temp.selectOne(ns+"selectRewardById", rewardId);
	}

	@Override
	public List<Reward> selectAllReward() {
		return temp.selectList(ns + "selectAllReward");
	}

	@Override
	public List<Reward> selectRewardByGameId(String gameId) {
		return temp.selectList(ns + " ", gameId);
	}
}
