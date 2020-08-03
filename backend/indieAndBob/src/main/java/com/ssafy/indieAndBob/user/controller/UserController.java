package com.ssafy.indieAndBob.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PostMapping("/account/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@RequestBody User request) {
		logger.info("==========signup==========");
		logger.info("user : " + request);
		ResponseEntity response = null;
		User u = userService.selectByEmail(request.getEmail());
		if (u == null) {
			u = userService.selectByNickname(request.getNickname());
			logger.info(" " + u);
			if(u == null) {
				if (userService.registerUser(request) == 1) {
					final BasicResponse result = new BasicResponse();
					result.status = true;
					result.data = "success";
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				final BasicResponse result = new BasicResponse();
				result.status = false;
				result.data = "duplicated nickname";
				response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		} else {
			final BasicResponse result = new BasicResponse();
			result.status = false;
			result.data = "duplicated email";
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/account/userinfo/{nickname}")
	@ApiOperation(value = "특정 회원정보")
	public Object selectUserByNickName(@PathVariable String nickname) {
		logger.info("==========selectUserByNickName==========");
		ResponseEntity response = null;
		User u = userService.selectByNickname(nickname);
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
	
	@GetMapping("/user/search")
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
	
	@PostMapping("/following")
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
	

	@GetMapping("/follower/{nickname}")
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
	
	@GetMapping("/following/{nickname}")
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
	
	@Delete("/unfollow")
	@ApiOperation(value = "언팔하기")
	public Object unFollow(HttpServletRequest request) {
		logger.info("==========unFollow==========");
		String follower = jwtService.getNickname(request);
		String following = request.getParameter("following");
		Follow deleteFollow = new Follow();
		deleteFollow.setFollower(follower);
		deleteFollow.setFollower(following);
		logger.info("unfollow : " + request);
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
	
	@GetMapping("/isfollowing")
	@ApiOperation(value="해당사람을 팔로우 하고 있는지 아닌지")
	public Object isFollowing(HttpServletRequest request) {
		String follower = request.getParameter("follower");
		String following = request.getParameter("following");
		logger.info("==========isFollowing==========");
		Follow follow = new Follow();
		follow.setFollower(follower);
		follow.setFollowing(following);
		logger.info("follow : " + follow);
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
			response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
