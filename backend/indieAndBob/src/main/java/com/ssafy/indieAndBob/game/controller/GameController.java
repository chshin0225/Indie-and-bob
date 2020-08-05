package com.ssafy.indieAndBob.game.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.dto.GameDetail;
import com.ssafy.indieAndBob.game.dto.GameLike;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.User;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	GameService gservice;
	
	@Autowired
	JwtService jwtService;
	
	@GetMapping("/gamelist/{page}")
	@ApiOperation(value="모든게임리스트 조회")
	public Object selectAllGame(@PathVariable int page) {
		logger.info("==========selectAllGame==========");
		ResponseEntity response = null;
		List<Game> gamelist = gservice.selectAllGame(page);
		if(gamelist.size()>=0) {
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
	
	@GetMapping("/admin/gamelist/{page}")
	@ApiOperation(value="모든게임리스트 조회")
	public Object selectAllGameAdmin(@PathVariable int page, HttpServletRequest req) {
		String nickname = jwtService.getNickname(req);
		logger.info("==========selectAllGame==========");
		ResponseEntity response = null;
		if(nickname.equals("admin")) {
			List<Game> gamelist = gservice.selectAllGameAdmin(page);
			if(gamelist.size()>=0) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				result.object = gamelist;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
		else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/game/{gameId}")
	@ApiOperation(value = "게임 아이디로 게임찾기")
	public Object selectGameById(@PathVariable String gameId) {
		logger.info("==========selectGameById==========");
		logger.info("gameid : " + gameId);
		ResponseEntity response = null;
		GameAll game = gservice.selectGameById(gameId);
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
	
	@PutMapping("/game")
	@ApiOperation(value = "게임 아이디로 게임 수정")
	public Object updateGameById(@RequestBody Game game) {
		logger.info("==========updateGameById==========");
		logger.info("gameid : " + game);
		ResponseEntity response = null;
		if(gservice.updateGame(game) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@DeleteMapping("/game/{gameId}")
	@ApiOperation(value = "게임 아이디로 게임 삭제")
	public Object deleteGameById(@PathVariable int gameId) {
		logger.info("==========deleteGameById==========");
		logger.info("gameid : " + gameId);
		ResponseEntity response = null;
		if(gservice.deleteGameById(gameId) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@PostMapping("/game/registergame")
	@ApiOperation(value = "게임 등록")
	public Object registerGame(@RequestBody GameAll request, HttpServletRequest req) {
//		String nickname = jwtService.getNickname(req);
		String nickname = "aa";
		logger.info("==========registerGame==========");
		logger.info("Game : " + request);
		logger.info("nickname = " + nickname);
		ResponseEntity response = null;
		request.setNickname(nickname);
		String img = request.getThumbnail();
		String[] extensions = img.split("\\.");
		String extension = extensions[extensions.length-1];
		
		int gameId = gservice.registerGame(request, extension);
		if (gameId != 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			Map<String,Object> obj = new HashMap<>();
			obj.put("extension", extension);
			obj.put("gameId", gameId);
			result.object = obj;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@PostMapping("/game/like")
	@ApiOperation(value = "게임 좋아요")
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
	public Object deleteGameLike(HttpServletRequest request) {
		logger.info("==========gameLike delete==========");
		String gameId = request.getParameter("gameId");
		String nickname = request.getParameter("nickname");
		GameLike like = new GameLike();
		like.setGameId(gameId);
		like.setNickname(nickname);
		logger.info("gameLike delete : " + like);
		ResponseEntity response = null;
		if (gservice.deleteGameLike(like) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/game/like/gamelist/{nickname}")
	@ApiOperation(value = "좋아요한 게임 리스트")
	public Object gameLikeList(@PathVariable String nickname) {
		logger.info("==========gameLikeList==========");
		logger.info("gameLikeList : " + nickname);
		ResponseEntity response = null;
		List<Game> games = new LinkedList<>();
		games = gservice.selectGameByNickname(nickname);
		if (games.size() >= 0) {
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
	@ApiOperation(value = "게임을 좋아요 한 유저 리스트 조회")
	public Object userLikeList(@PathVariable String gameId) {
		logger.info("==========userLikeList==========");
		logger.info("userLikeList : " + gameId);
		ResponseEntity response = null;
		List<User> users = new LinkedList<>();
		users = gservice.selectUserByGameId(gameId);
		if (users.size() >= 0) {
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
	@ApiOperation(value = "이 게임을 좋아하는지 여부 확인")
	public Object isLike(HttpServletRequest request) {
		String nickname = request.getParameter("nickname");
		String gameId = request.getParameter("gameId");
		GameLike like = new GameLike();
		like.setNickname(nickname);
		like.setGameId(gameId);
		logger.info("==========islike==========");
		logger.info("islike : " + like);
		ResponseEntity response = null;
		if (gservice.isLike(like) != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@PutMapping("/game/approve")
	@ApiOperation(value = "게임 승인 여부")
	public Object approve(@RequestBody Game game) {
		logger.info("==========approve==========");
		logger.info("approve : " + game);
		ResponseEntity response = null;
		if (gservice.updateApprove(game) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/madegame/{nickname}")
	@ApiOperation(value = "내가 만든 프로젝트 리스트 조회")
	public Object selectMadeGameByNickname(@PathVariable String nickname) {
		logger.info("==========gameByNickname==========");
		logger.info("gameByNickname : " + nickname);
		ResponseEntity response = null;
		List<GameAll> games = gservice.selectMadeGameByNickname(nickname);
		if (games != null) {
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
	
}
