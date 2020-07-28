package com.ssafy.indieAndBob.game.controller;

import java.util.LinkedList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameComment;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.service.GameCommentService;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class GameCommentController {

	private static final Logger logger = LoggerFactory.getLogger(GameCommentController.class);
	
	@Autowired
	GameCommentService gcservice;
	
	@PostMapping("/game/comment")
	@ApiOperation(value = "게임 댓글 등록")
	public Object registerGameComment(@RequestBody GameComment comment) {
		logger.info("==========registerGameComment==========");
		logger.info("registerGameComment : " + comment);
		ResponseEntity response = null;
		if (gcservice.registerComment(comment) != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/game/comment/{gameId}")
	@ApiOperation(value = "게임에 등록된 댓글 조회")
	public Object selectCommentByGameId(@PathVariable String gameId) {
		logger.info("==========selectCommentByGameId==========");
		logger.info("selectCommentByGameId : " + gameId);
		ResponseEntity response = null;
		List<GameComment> comments = gcservice.selectCommentByGameId(gameId);
		if (comments.size() != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = comments;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
