package com.ssafy.indieAndBob.reward.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.reward.dto.Reward;
import com.ssafy.indieAndBob.reward.service.RewardService;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class RewardController {

	@Autowired
	RewardService rservice;
	private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

	@GetMapping("/reward")
	@ApiOperation(value = "모든리워드찾기")
	public Object selectAllReward() {
		logger.info("===========selectAllReward==========");

		ResponseEntity response = null;
		List<Reward> rewardlist = rservice.selectAllReward();
		if(rewardlist.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = rewardlist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/reward/hj/{rewardId}")
	@ApiOperation(value = "리워드아이디로 리워드찾기")
	public Object selectRewardById(@PathVariable String rewardId) {

		logger.info("===========selectRewardById==========");
		logger.info("rewardId : " + rewardId);

		ResponseEntity response = null;
		Reward reward = rservice.selectRewardById(rewardId);
		if(!reward.equals(null)) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = reward;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	@GetMapping("/rewards/bygameid/{gameId}")
	@ApiOperation(value = "게임아이디에 해당하는 리워드 찾기")
	public Object selectRewardByGameId(@PathVariable String gameId) {
		logger.info("===========selectRewardByGameId==========");
		logger.info("gameId : " + gameId);
		ResponseEntity response = null;
		List<Reward> rewardlist = rservice.selectRewardByGameId(gameId);
		if(rewardlist.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = rewardlist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}


	@PostMapping("/reward/registerreward")
	@ApiOperation(value = "리워드등록")
	public Object registerReward(@RequestBody Reward request) {
		logger.info("===========registerGame==========");
		logger.info("reward : " + request);
		ResponseEntity response = null;
		if (rservice.registerReward(request) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@PutMapping("/reward/updatereward")
	@ApiOperation(value = "리워드수정")
	public Object updateRewardByRewardId(@RequestBody Reward reqest) {
		logger.info("===========updateRewardByRewardId==========");
		logger.info("reward to update: " + reqest);
		ResponseEntity response = null;
		if(rservice.updateRewardByRewardId(reqest) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@DeleteMapping("/reward/deletereward")
	@ApiOperation(value = "리워드삭제")
	public Object deleteRewardByRewardId(@RequestBody int rewardId) {
		logger.info("===========deleteRewardByRewardId==========");
		logger.info("rewardId : "+rewardId);
		ResponseEntity response = null;
		if(rservice.deleteRewardByRewardId(rewardId) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}


}
