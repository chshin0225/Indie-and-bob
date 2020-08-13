package com.ssafy.indieAndBob.recommand.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.qna.dto.QnaVO;
import com.ssafy.indieAndBob.recommand.dto.GenrebaseRecommand;
import com.ssafy.indieAndBob.recommand.service.GenrebaseRecommandService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class GenrebaseRecommandController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	GenrebaseRecommandService service;
	@Autowired
	JwtService jwtService;
	
	@GetMapping("/api/recommand/genrebase")
	@ApiOperation(value = "장르기반 추천")
	public Object recommandGenrebase(HttpServletRequest req) {
		logger.info("==========[recomand genre base]==========");
		ResponseEntity response = null;
		String nickname = jwtService.getNickname(req);		
		List<GenrebaseRecommand> recommandList = service.recommandGenrebase(nickname);
		if(recommandList == null) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("fail to recommand");
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = recommandList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
}
