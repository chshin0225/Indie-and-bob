package com.ssafy.indieAndBob.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.dto.Follow;
import com.ssafy.indieAndBob.user.dto.User;
import com.ssafy.indieAndBob.user.dto.UserAll;
import com.ssafy.indieAndBob.user.service.EmailService;
import com.ssafy.indieAndBob.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;
	@Autowired
	JwtService jwtService;

	@PostMapping("/api/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestBody User user, HttpServletResponse res) {
		logger.info("==========login==========");
		logger.info("user : " + user);
		ResponseEntity response = null;
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
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "없는 이메일이거나 틀린 비밀번호 입니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@PostMapping("/api/account/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@RequestBody UserAll request) {
		logger.info("==========signup==========");
		logger.info("user : " + request);
		ResponseEntity response = null;
		User u = userService.selectByEmail(request.getEmail());
		if (u == null) {
			UserAll u2 = userService.selectByNickname(request.getNickname());
			logger.info(" " + u);
			if(u2 == null) {
				if(request.getProfile() != null) {
					String img = request.getProfile();
					String[] extensions = img.split("\\.");
					String extension = extensions[extensions.length-1];
					request.setProfile("user/" + request.getNickname() + "/" + request.getNickname() + "." + extension);
				}
				if (userService.registerUser(request) == 1) {
					final BasicResponse result = new BasicResponse();
					result.status = true;
					result.data = "success";
					response = new ResponseEntity<>(result, HttpStatus.OK);
//					emailService.sendSimpleMessage(request.getEmail(), "Indie And Bob 가입 환영 메일", "가입을 환영합니다~~!!");
				} else {
					response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				final BasicResponse result = new BasicResponse();
				result.status = false;
				result.data = "이미 사용중인 닉네임입니다.";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "이미 가입된 이메일입니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/api/account/userinfo/{nickname}")
	@ApiOperation(value = "특정 회원정보")
	public Object selectUserByNickName(@PathVariable String nickname) {
		logger.info("==========selectUserByNickName==========");
		ResponseEntity response = null;
		UserAll u = userService.selectByNickname(nickname);
		if (u == null) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = u;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/api/user/search")
	@ApiOperation(value = "유저 검색")
	public Object userSearch(HttpServletRequest request) {
		logger.info("==========userSearch==========");
		String keyword = request.getParameter("keyword");
		logger.info("keyword : " + keyword);
		ResponseEntity response = null;
		List<User> users = userService.searchUser(keyword);
		if (users.size() >= 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = users;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PutMapping("/api/account/userinfo")
	@ApiOperation(value = "회원 정보 수정")
	public Object changeUserInfo(@RequestBody UserAll request) {
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
	
	@PostMapping("/api/following")
	@ApiOperation(value = "팔로우하기")
	public Object following(@RequestBody Follow request, HttpServletRequest req) {
		logger.info("==========following==========");
		logger.info("follow : " + request);
		String nickname = jwtService.getNickname(req);
		request.setFollower(nickname);
		ResponseEntity response = null;
		if(userService.registerFollow(request) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	

	@GetMapping("/api/follower/{nickname}")
	@ApiOperation(value = "팔로워리스트 불러오기")
	public Object getFollower(@PathVariable String nickname) {
		logger.info("==========getFollower==========");
		logger.info("userId : " + nickname);
		ResponseEntity response = null;
		List<User> followerlist = userService.getFollower(nickname);
		logger.info("list : " + followerlist);
		if(followerlist.size() >= 0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = followerlist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/api/following/{nickname}")
	@ApiOperation(value = "팔로잉리스트 불러오기")
	public Object getFollowing(@PathVariable String nickname) {
		logger.info("==========getFollowing==========");
		logger.info("userId : " + nickname);
		ResponseEntity response = null;
		List<User> followinglist = userService.getFollowing(nickname);
		if(followinglist.size()>=0) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = followinglist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@DeleteMapping("/api/unfollow")
	@ApiOperation(value = "언팔하기")
	public Object unFollow(HttpServletRequest request) {
		logger.info("==========unFollow==========");
		String follower = jwtService.getNickname(request);
		String following = request.getParameter("following");
		Follow deleteFollow = new Follow();
		deleteFollow.setFollower(follower);
		deleteFollow.setFollowing(following);
		logger.info("unfollow : " + following);
		ResponseEntity response = null;
		if(userService.deleteFollowing(deleteFollow) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping("/api/isfollowing")
	@ApiOperation(value="해당사람을 팔로우 하고 있는지 아닌지")
	public Object isFollowing(HttpServletRequest request) {
		String follower = jwtService.getNickname(request);
		String following = request.getParameter("following");
		logger.info("==========isFollowing==========");
		Follow follow = new Follow();
		follow.setFollower(follower);
		follow.setFollowing(following);
		logger.info("isfollow : " + follow);
		ResponseEntity response = null;
		if(userService.isFollowing(follow)) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = true;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = false;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
}
