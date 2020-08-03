package com.ssafy.indieAndBob.kakaopay.dao;

import java.util.List;

import com.ssafy.indieAndBob.kakaopay.dto.Funding;

public interface FundingDao {

	Funding selectFundingByFundingId(int fundingId);
	List<Funding> selectFundingByGameId(int gameId);
	int registerFunding(Funding funding);
	int deleteFundingByFundingId(int fundingId);
	List<Funding> selectFundingByNickname(String nickname);
	
}
