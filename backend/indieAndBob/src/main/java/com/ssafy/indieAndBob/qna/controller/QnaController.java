package com.ssafy.indieAndBob.qna.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.jwt.service.JwtService;
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
	public Object create(@RequestBody QnaVO vo) {
		logger.info("==========[qna create]==========");
		ResponseEntity response = null;
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
	
	@GetMapping("/qna/read")
	@ApiOperation(value = "Q&A 조회")
	public Object read(@RequestBody int qna_id) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		QnaVO vo = qnaService.read(qna_id);
		if(vo == null) {
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
	
	@PutMapping("/qna/update")
	@ApiOperation("Q&A 수정")
	public Object update(@RequestBody QnaVO vo) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
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
	
	@DeleteMapping("/qna/delete")
	@ApiOperation("Q&A 삭제")
	public Object delete(@RequestBody int qna_id) {
		logger.info("==========[qna read]==========");
		ResponseEntity response = null;
		int res = qnaService.delete(qna_id);
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
}
