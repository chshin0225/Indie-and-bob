package com.ssafy.indieAndBob.kakaopay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GenreDao;
import com.ssafy.indieAndBob.game.dto.FundingGame;
import com.ssafy.indieAndBob.game.dto.MyGameSearch;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.kakaopay.dao.FundingDao;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;

@Service
public class FundingServiceImpl implements FundingService {

	@Autowired
	FundingDao fundingdao;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	GenreDao genredao;
	
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
	public List<FundingGame> selectFundingByNickname(MyGameSearch search) {
		List<FundingGame> games = fundingdao.selectFundingByNickname(search);
		for(FundingGame game : games) {
			List<String> genreName = genredao.selectGenreNameByGameId(game.getGameId());
			game.setGenreName(genreName);
		}
		return games;
	}

	@Override
	public List<Funding> selectFundingByGameId(int gameId) {
		return fundingdao.selectFundingByGameId(gameId);
	}

}
