package com.ssafy.indieAndBob.game.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.Genre;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.game.service.GenreService;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class GenreController {

	private static final Logger logger = LoggerFactory.getLogger(GenreController.class);
	
	@Autowired
	GenreService gservice;
	
	@GetMapping("/genre")
	@ApiOperation(value="모든 게임 장르 조회")
	public Object selectAllGenre() {
		logger.info("==========selectAllGenre==========");
		ResponseEntity response = null;
		List<Genre> genreList = gservice.selectAllGenre();
		if(genreList.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = genreList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
