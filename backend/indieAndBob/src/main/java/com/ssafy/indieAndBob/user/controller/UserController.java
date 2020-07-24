package com.ssafy.indieAndBob.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	JwtService jwtService;

	@PostMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestBody User user, HttpServletResponse res) {
		logger.info("==========login==========");
		ResponseEntity response = null;
		logger.info(""+user);
		User u = userService.selectByEmailAndPassword(user);
		if (u != null) {
			String token = jwtService.create(u);
			res.setHeader("jwt-auth-token", token);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = token;
			result.object = u;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PostMapping("/account/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@RequestBody User request) {
		logger.info("==========signup==========");
		ResponseEntity response = null;
		User u = userService.selectByEmail(request.getEmail());
		if (u == null) {
			if (userService.registerUser(request) == 1) {
				final BasicResponse result = new BasicResponse();
				result.status = true;
				result.data = "success";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "duplicated email";
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PutMapping("/account/userinfo")
	@ApiOperation(value = "회원 정보 수정")
	public Object changeUserInfo(@RequestBody User request) {
		logger.info("==========changeUserInfo==========");
		ResponseEntity response = null;
		if (userService.changeUserInfo(request) == 1) {
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
