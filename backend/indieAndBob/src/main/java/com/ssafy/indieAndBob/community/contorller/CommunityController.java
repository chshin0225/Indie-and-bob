package com.ssafy.indieAndBob.community.contorller;

import java.util.HashMap;
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

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.MyCommunitySearch;
import com.ssafy.indieAndBob.community.service.CommunityService;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class CommunityController {

	private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CommunityService communityService;
	
	@PostMapping("/api/community")
	@ApiOperation(value="커뮤니티 글 작성")
	public Object registerCommunity(@RequestBody Community community,
			HttpServletRequest request) {
		logger.info("==========registerCommunity==========");
		String nickname = jwtService.getNickname(request);
		community.setNickname(nickname);
		ResponseEntity response = null;
		if(communityService.insertCommunity(community) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/api/communitylist/{page}")
	@ApiOperation(value="커뮤니티 글 전부 조회")
	public Object listCommunity(@PathVariable int page) {
		logger.info("==========listCommunity==========");
		ResponseEntity response = null;
		page = (page - 1) * 10;
		List<Community> list = communityService.listCommunity(page);
		int count = communityService.listCommunityCount();
		Map<String, Object> map = new HashMap<>();
		if(list != null) {
			map.put("list", list);
			map.put("count", count);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = map;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/api/community/{communityId}")
	@ApiOperation(value="커뮤니티 글 세부 조회")
	public Object communityDetail(@PathVariable int communityId) {
		logger.info("==========communityDetail==========");
		ResponseEntity response = null;
		Community community = communityService.communityDetail(communityId);
		if(community != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = community;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@PutMapping("/api/community")
	@ApiOperation(value="커뮤니티 글 수정")
	public Object updateCommunity(@RequestBody Community community) {
		logger.info("==========updateCommunity==========");
		ResponseEntity response = null;
		if(communityService.updateCommunity(community) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@DeleteMapping("/api/community/{communityId}")
	@ApiOperation(value="커뮤니티 글 삭제")
	public Object deleteCommunity(@PathVariable int communityId) {
		logger.info("==========deleteCommunity==========");
		ResponseEntity response = null;
		if(communityService.deleteCommunity(communityId) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/api/community/{nickname}/{page}")
	@ApiOperation(value="내가 작성한 글 조회")
	public Object listMyCommunity(@PathVariable String nickname, @PathVariable int page) {
		logger.info("==========listMyCommunity==========");
		ResponseEntity response = null;
		page = (page - 1) * 10;
		MyCommunitySearch search = new MyCommunitySearch(nickname, page);
		List<Community> list = communityService.listMyCommunity(search);
		int count = communityService.listMyCommunityCount(nickname);
		Map<String, Object> map = new HashMap<>();
		if(list != null) {
			map.put("list", list);
			map.put("count", count);
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = map;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
