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
	public Reward selectRewardById(int rewardId) {
		
		return temp.selectOne(ns+"selectRewardById", rewardId);
	}

	@Override
	public List<Reward> selectAllReward() {
		return temp.selectList(ns + "selectAllReward");
	}

	@Override
	public List<Reward> selectRewardByGameId(int gameId) {
		return temp.selectList(ns + "selectRewardByGameId", gameId);
	}

	@Override
	public int updateRewardByRewardId(Reward reward) {
		return temp.update(ns + "updateRewardByRewardId",reward);
	}

	@Override
	public int deleteRewardByRewardId(int rewardId) {
		return temp.delete(ns + "deleteRewardByRewardId", rewardId);
	}

	@Override
	public int buyReward(int rewardId) {
		return temp.update(ns + "buyReward", rewardId);
	}
	
}
