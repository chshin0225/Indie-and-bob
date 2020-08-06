package com.ssafy.indieAndBob.kakaopay.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.game.dto.FundingGame;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;

@Repository
public class FundingDaoImpl implements FundingDao{
	private static String ns = "com.ssafy.indieAndBob.mapper.funding.";
	
	@Autowired
	SqlSessionTemplate temp;

	@Override
	public Funding selectFundingByFundingId(int fundingId) {
		return temp.selectOne(ns+"selectFundingByFundingId", fundingId);
	}

	@Override
	public List<Funding> selectFundingByRewardId(int rewardId) {
		return temp.selectList(ns+"selectFundingByRewardId", rewardId);
	}

	@Override
	public int registerFunding(Funding funding) {
		if(temp.insert(ns+"registerFunding", funding)!=1) {
			return 0;
		}
		return funding.getFundingId();
	}

	@Override
	public int deleteFundingByFundingId(int fundingId) {
		return temp.delete(ns+"deleteFundingByFundingId",fundingId);
	}

	@Override
	public List<FundingGame> selectFundingByNickname(String nickname) {
		return temp.selectList(ns+"selectFundingByNickname", nickname);
	}

	@Override
	public List<Funding> selectFundingByGameId(int gameId) {
		return temp.selectList(ns + "selectFundingByGameId", gameId);
	}
	
	

}
