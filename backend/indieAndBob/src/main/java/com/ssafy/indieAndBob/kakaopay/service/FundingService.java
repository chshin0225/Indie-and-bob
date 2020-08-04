package com.ssafy.indieAndBob.kakaopay.service;

import java.util.List;

import com.ssafy.indieAndBob.kakaopay.dto.Funding;

public interface FundingService {
	Funding selectFundingByFundingId(int fundingId);
	List<Funding> selectFundingByRewardId(int rewardId);
	int registerFunding(Funding funding);
	int deleteFundingByFundingId(int fundingId);
	List<Funding> selectFundingByNickname(String nickname);
}
