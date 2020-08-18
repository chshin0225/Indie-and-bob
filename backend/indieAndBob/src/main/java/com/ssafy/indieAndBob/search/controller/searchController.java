package com.ssafy.indieAndBob.search.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.search.service.SearchService;
import com.ssafy.indieAndBob.user.dto.User;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class searchController {

	private static final Logger logger = LoggerFactory.getLogger(searchController.class);
	
	@Autowired
	SearchService searchService;
	
	@GetMapping("/api/search")
	@ApiOperation(value="통합 검색")
	public Object search(HttpServletRequest request) {
		logger.info("==========search==========");
		ResponseEntity response = null;
		String keyword = request.getParameter("keyword");
		List<User> userlist = searchService.userSearch(keyword);
		List<GameAll> gamelist = searchService.gameSearch(keyword);
		List<Community> communitylist = searchService.communitySearch(keyword);
		
		if(userlist != null && gamelist != null && communitylist != null) {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> map = new HashMap<>();
			map.put("user", userlist);
			map.put("game", gamelist);
			map.put("community", communitylist);
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
