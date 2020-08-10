package com.ssafy.indieAndBob.qna.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.qna.dto.QnaGameVO;
import com.ssafy.indieAndBob.qna.dto.QnaVO;
import com.ssafy.indieAndBob.qna.service.QnaService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.controller.UserController;
import com.ssafy.indieAndBob.user.service.EmailService;
import com.ssafy.indieAndBob.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class QnaController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	QnaService qnaService;
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/qna/create")
	@ApiOperation(value = "Q&A 등록")
	public Object create(@RequestBody QnaVO vo, HttpServletRequest req) {
		logger.info("==========[qna create]==========");
		ResponseEntity response = null;
		vo.setNickname(jwtService.getNickname(req));
		int res = qnaService.create(vo);
		if(res == 0) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("fail to create");
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			logger.info("success");
		}
		return response;
	}
	
	@GetMapping("/qna/read/{qnaId}")
	@ApiOperation(value = "Q&A 조회")
	public Object read(@PathVariable int qnaId) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		QnaVO vo = qnaService.read(qnaId);
		if(vo == null) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("fail to read");
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			logger.info(vo.getQnaId() + vo.getTitle() + vo.getGameId() + vo.getNickname());
			logger.info("success");
		}
		return response;
	}
	
	@PutMapping("/qna/update")
	@ApiOperation("Q&A 수정")
	public Object update(@RequestBody QnaVO vo, HttpServletRequest req) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		vo.setNickname(jwtService.getNickname(req));
		int res = qnaService.update(vo);
		if(res == 0) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("fail to create");
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			logger.info("success");
		}
		return response;
	}
	
	@DeleteMapping("/qna/delete/{qnaId}")
	@ApiOperation("Q&A 삭제")
	public Object delete(@PathVariable int qnaId) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		int res = qnaService.delete(qnaId);
		if(res == 0) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("fail to create");
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			logger.info("success");
		}
		return response;		
	}
	
	@GetMapping("/qna/readByGameID/{gameId}")
	@ApiOperation("게임ID로 조회하기")
	public Object readByGameId(@PathVariable int gameId, HttpServletRequest req) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		
		//no secret 먼저 받고 -> 관리자인지 확인 -> 관리자이면 시크릿도 전부 추가 -> 아니면 시크릿 중에 자신의 id만 확인해서 한다.
		List<QnaVO> qnaList = qnaService.readByGameIdNoSecret(gameId);
		String nickname = jwtService.getNickname(req);
		QnaGameVO qna_game_vo = new QnaGameVO(gameId, nickname);
		if(qnaService.isDeveloper(qna_game_vo)) {	// 관리자
			List<QnaVO> secretList = qnaService.readByGameIdAllSecret(gameId);
			if(secretList != null) {
				qnaList.addAll(secretList);
			}
		}else {		// 질문자
			List<QnaVO> secretList = qnaService.readByGameIdMySecret(qna_game_vo);
			if(secretList != null) {
				qnaList.addAll(secretList);
			}
		}
		
		if(qnaList == null) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = qnaList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}		
		return response; 
	}
	
	@GetMapping("/qna/readByGameIDNoSecret/{gameId}")
	@ApiOperation("게임ID로 조회하기")
	public Object readByGameIdNoSecret(@PathVariable int gameId) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		
		//no secret 먼저 받고 -> 관리자인지 확인 -> 관리자이면 시크릿도 전부 추가 -> 아니면 시크릿 중에 자신의 id만 확인해서 한다.
		List<QnaVO> qnaList = qnaService.readByGameIdNoSecret(gameId);
		if(qnaList == null) {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = qnaList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}		
		return response; 
	}
}
