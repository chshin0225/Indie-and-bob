package com.ssafy.indieAndBob.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.Game;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class GameController {

	@Autowired
	GameService gservice;
	
	@GetMapping("/game/{gameId}")
	@ApiOperation(value = "게임아이디로 게임찾기")
	public Object selectGameById(@PathVariable String gameId) {
		ResponseEntity<Game> response = null;
		Game game = gservice.selectGameById(gameId);
		if(!game.equals(null)) {
			return new ResponseEntity<Game> (game, HttpStatus.OK);
		}
		return new ResponseEntity<Game> (game, HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("/game/registergame")
	@ApiOperation(value = "펀딩할 게임등록")
	public Object registerGame(@RequestBody Game request) {
		ResponseEntity response = null;
		if (gservice.registerGame(request) == 1) {
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
