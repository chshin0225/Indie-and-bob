package com.ssafy.indieAndBob.kakaopay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.kakaopay.dao.FundingDao;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;

@Service
public class FundingServiceImpl implements FundingService {

	@Autowired
	FundingDao fundingdao;
	
	@Autowired
	GameService gameService;
	
	@Override
	public Funding selectFundingByFundingId(int fundingId) {
		return fundingdao.selectFundingByFundingId(fundingId);
	}

	@Override
	public List<Funding> selectFundingByRewardId(int rewardId) {
		return fundingdao.selectFundingByRewardId(rewardId);
	}

	@Override
	public int registerFunding(Funding funding) {
		int fundingId = fundingdao.registerFunding(funding);
		gameService.fundingGame(funding);
		return fundingId;
	}

	@Override
	public int deleteFundingByFundingId(int fundingId) {
		return fundingdao.deleteFundingByFundingId(fundingId);
	}

	@Override
	public List<Funding> selectFundingByNickname(String nickname) {
		return fundingdao.selectFundingByNickname(nickname);
	}

}
