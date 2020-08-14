package com.ssafy.indieAndBob.kakaopay.controller;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.FundingGame;
import com.ssafy.indieAndBob.game.dto.MyGameSearch;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.kakaopay.dto.KakaoPayApprovalVO;
import com.ssafy.indieAndBob.kakaopay.service.FundingService;
import com.ssafy.indieAndBob.kakaopay.service.KakaoPay;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.reward.service.RewardService;

import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.java.Log;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@Log
@RestController
public class KakaopayController {
	@Setter(onMethod_ = @Autowired)

	private KakaoPay kakaopay;

	@Autowired
	FundingService fservice;
	@Autowired
	RewardService rewardService;
	@Autowired
	JwtService jservice;

	@PostMapping("/api/kakaoPay")
	@ApiOperation(value = "결제하기")
	public Object kakaoPay(@RequestBody Funding request, HttpServletResponse res, HttpServletRequest req) {
		log.info("kakaoPay post............................................");
		log.info("funding : " + request);
		ResponseEntity response = null;
		
		if(rewardService.buyReward(request.getRewardId()) == 0) {
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "sold out";
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		}

		String nickname = jservice.getNickname(req);
		request.setNickname(nickname);
		String toPay = kakaopay.kakaoPayReady(request);
		if( toPay != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = toPay;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}

	@GetMapping("/api/kakaoPaySuccess")//결제가된다면 url에 pg_token이 포함되어있을것이다
	@ApiOperation(value = "결제내역")
	public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token,@RequestParam("nickname") String nickname,
			@RequestParam("gameId") int gameId,
			@RequestParam("rewardId") int rewardId,
			@RequestParam("money") int money,Model model, HttpServletResponse res) {
		log.info("kakaoPaySuccess get............................................");
		log.info("kakaoPaySuccess pg_token : " + pg_token);
		KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token,nickname,rewardId,money);
		model.addAttribute("info", info);
		// model.addAttribute("info",kakaopay.kakaoPayInfo(pg_token));//정보들
		ResponseEntity response = null;
		
		if(info == null) {
			return;
		}
		Funding funding = new Funding();
		funding.setGameId(gameId);
		funding.setRewardId(rewardId);
		funding.setNickname(nickname);
		funding.setMoney(money);
		int fundingId = fservice.registerFunding(funding);
		
		funding.setFundingId(fundingId);
		if(fundingId==0) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if( info != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = info;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//return response;
		try {
			//res.sendRedirect("http://i3a105.p.ssafy.io:3000/home");
			String encodedNickname = URLEncoder.encode(nickname, "UTF-8");
			res.sendRedirect("http://i3a105.p.ssafy.io/purchase/" + rewardId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/api/kakaoPayFail")
	@ApiOperation(value = "카카오페이 결제 실패")
	public void kakaoPayFail(@RequestParam("pg_token") String pg_token,@RequestParam("nickname") String nickname,
			@RequestParam("gameId") int gameId,
			@RequestParam("rewardId") int rewardId,
			@RequestParam("money") int money,Model model, HttpServletResponse res) {
		log.info("kakaoPayFail get............................................");
		log.info("kakaoPayFail pg_token : " + pg_token);
		KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token,nickname,rewardId,money);
		model.addAttribute("info", info);
		// model.addAttribute("info",kakaopay.kakaoPayInfo(pg_token));//정보들
		ResponseEntity response = null;
	}
	
	@GetMapping("/api/kakaoPayCancel")
	@ApiOperation(value = "카카오페이 결제 취소")
	public void kakaoPayCancel(@RequestParam("pg_token") String pg_token,@RequestParam("nickname") String nickname,
			@RequestParam("gameId") int gameId,
			@RequestParam("rewardId") int rewardId,
			@RequestParam("money") int money,Model model, HttpServletResponse res) {
		log.info("kakaoPayCancel get............................................");
		log.info("kakaoPayCancel pg_token : " + pg_token);
		KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token,nickname,rewardId,money);
		model.addAttribute("info", info);
		// model.addAttribute("info",kakaopay.kakaoPayInfo(pg_token));//정보들
		ResponseEntity response = null;
	} 
	
	@GetMapping("/api/fundingByFundingId")
	@ApiOperation(value = "펀딩아이디로 펀딩찾기")
	public Object selectFundingByFundingId(@RequestParam("fundingId") int fundingId) {
		log.info("-------------selectFundingByFundingId-----------------");
		log.info("fundingId : " + fundingId);
		ResponseEntity response = null;

		Funding funding = fservice.selectFundingByFundingId(fundingId);

		if(funding != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = funding;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/api/fundingByGameId/{gameId}")
	@ApiOperation(value = "게임 아이디로 해당 펀딩리스트찾기")
	public Object selectFundingByGameId(@PathVariable int gameId) {
		log.info("-------------selectFundingByRewardId-----------------");
		log.info("rewardId : " + gameId);
		ResponseEntity response = null;

		List<Funding> fundinglist = fservice.selectFundingByGameId(gameId);

		if(fundinglist.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = fundinglist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/api/fundingByRewardId/{rewardId}")
	@ApiOperation(value = "리워드 아이디로 해당 펀딩리스트찾기")
	public Object selectFundingByRewardId(@PathVariable int rewardId) {
		log.info("-------------selectFundingByRewardId-----------------");
		log.info("rewardId : " + rewardId);
		ResponseEntity response = null;

		List<Funding> fundinglist = fservice.selectFundingByRewardId(rewardId);

		if(fundinglist.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = fundinglist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	@GetMapping("/api/fundingByNickname/{nickname}/{page}")
	@ApiOperation(value = "닉네임으로 펀딩리스트 찾기")
	public Object selectFundingByNickname(@PathVariable String nickname, @PathVariable int page) {
		log.info("-------------selectFundingByNickname-----------------");
		log.info("nickname : " + nickname);
		ResponseEntity response = null;
		MyGameSearch search = new MyGameSearch(nickname, page);
		List<FundingGame> fundingGames = fservice.selectFundingByNickname(search);
		if(fundingGames.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = fundingGames;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@DeleteMapping("/api/deleteByFundingId/{fundingId}")
	@ApiOperation(value = "펀딩아이디로 삭제하기")
	public Object deleteFundingByFundingId(@PathVariable int fundingId) {
		log.info("-------------deleteFundingByFundingId-----------------");
		log.info("fundingId : " + fundingId);
		ResponseEntity response = null;

		if(fservice.deleteFundingByFundingId(fundingId)==1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
