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
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	GameService gservice;
	
	@GetMapping("/game")
	@ApiOperation(value="모든게임리스트")
	public Object selectAllGame() {
		logger.info("==========selectAllGame==========");
		ResponseEntity response = null;
		List<Game> gamelist = gservice.selectAllGame();
		if(gamelist.size()>0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = gamelist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/game/{gameId}")
	@ApiOperation(value = "게임아이디로 게임찾기")
	public Object selectGameById(@PathVariable String gameId) {
		logger.info("==========selectGameById==========");
		logger.info("gameid : " + gameId);
		ResponseEntity response = null;
		Game game = gservice.selectGameById(gameId);
		if(!game.equals(null)) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = game;
			response = new ResponseEntity<>(result, HttpStatus.OK);
			
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@PostMapping("/game/registergame")
	@ApiOperation(value = "펀딩할 게임등록")
	public Object registerGame(@RequestBody Game request) {
		logger.info("==========registerGame==========");
		logger.info("Game : " + request);
		ResponseEntity response = null;
		int gameId = gservice.registerGame(request);
		if (gameId != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = gameId;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@PostMapping("/game/like")
	@ApiOperation(value = "게임 좋아요 등록")
	public Object gameLike(@RequestBody GameLike request) {
		logger.info("==========gameLike post==========");
		logger.info("gameLike post : " + request);
		ResponseEntity response = null;
		if (gservice.gameLike(request) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@DeleteMapping("/game/like")
	@ApiOperation(value = "게임 좋아요 삭제")
	public Object deleteGameLike(@RequestBody GameLike request) {
		logger.info("==========gameLike delete==========");
		logger.info("gameLike delete : " + request);
		ResponseEntity response = null;
		if (gservice.deleteGameLike(request) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/game/like/gamelist/{email}")
	@ApiOperation(value = "좋아하는 게임 리스트")
	public Object gameLikeList(@PathVariable String email) {
		logger.info("==========gameLikeList==========");
		logger.info("gameLikeList : " + email);
		ResponseEntity response = null;
		List<Game> games = new LinkedList<>();
		games = gservice.selectGameByEmail(email);
		if (games.size() != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = games;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/game/like/userlist/{gameId}")
	@ApiOperation(value = "게임을 좋아하는 사람 리스트")
	public Object userLikeList(@PathVariable String gameId) {
		logger.info("==========userLikeList==========");
		logger.info("userLikeList : " + gameId);
		ResponseEntity response = null;
		List<User> users = new LinkedList<>();
		users = gservice.selectUserByGameId(gameId);
		if (users.size() != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = users;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/game/islike")
	@ApiOperation(value = "좋아하는 게임 리스트")
	public Object isLike(HttpServletRequest request) {
		String email = request.getParameter("email");
		String gameId = request.getParameter("gameId");
		GameLike like = new GameLike(email, gameId);
		logger.info("==========islike==========");
		logger.info("islike : " + like);
		ResponseEntity response = null;
		if (gservice.isLike(like) != null) {
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
