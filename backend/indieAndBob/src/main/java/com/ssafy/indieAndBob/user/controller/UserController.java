package com.ssafy.indieAndBob.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class UserController {
	@Autowired
    UserService userService;
	
	@PostMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody User user) {
        
        ResponseEntity response = null;
        
        User u = userService.selectByEmailAndPassword(user);
        if(u != null) {
        	final BasicResponse result = new BasicResponse();
        	result.status = true;
        	result.data = "success";
        	response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
        	response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }
	
	@PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@RequestBody User request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
       ResponseEntity response = null;
       
       
        

        return response;
    }
}
