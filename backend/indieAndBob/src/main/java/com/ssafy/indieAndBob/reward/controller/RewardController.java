package com.ssafy.indieAndBob.reward.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.reward.dto.Reward;
import com.ssafy.indieAndBob.reward.service.RewardService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class RewardController {

	@Autowired
	RewardService rservice;

	@GetMapping("/reward/{rewardId}")
	@ApiOperation(value = "리워드아이디로 리워드찾기")
	public Object selectRewardById(@PathVariable String rewardId) {
		ResponseEntity<Reward> response = null;
		Reward reward = rservice.selectRewardById(rewardId);
		if(!reward.equals(null)) {
			return new ResponseEntity<Reward> (reward, HttpStatus.OK);
		}
		return new ResponseEntity<Reward> (reward, HttpStatus.NO_CONTENT);
	}


	@PostMapping("/reward/registerreward")
	@ApiOperation(value = "리워드등록")
	public Object registerGame(@RequestBody Reward request) {
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


}
