package com.ssafy.indieAndBob.community.contorller;

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

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.CommunityComment;
import com.ssafy.indieAndBob.community.service.CommunityCommentService;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class CommunityCommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommunityCommentController.class);
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CommunityCommentService commentService;
	
	@PostMapping("/community/comment")
	@ApiOperation(value="커뮤니티 댓글 작성")
	public Object registerCommunityComment(@RequestBody CommunityComment comment,
			HttpServletRequest request) {
		logger.info("==========registerCommunityComment==========");
		String nickname = jwtService.getNickname(request);
		comment.setNickname(nickname);
		ResponseEntity response = null;
		if(commentService.insertCommunityComment(comment) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@GetMapping("/community/comment/{communityId}")
	@ApiOperation(value="커뮤니티 댓글 조회")
	public Object listCommunityComment(@PathVariable int communityId) {
		logger.info("==========listCommunityComment==========");
		ResponseEntity response = null;
		List<Community> list = commentService.listCommunityComment(communityId);
		if(list != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = list;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@PutMapping("/community/comment")
	@ApiOperation(value="커뮤니티 댓글 수정")
	public Object updateCommunityComment(@RequestBody CommunityComment comment) {
		logger.info("==========updateCommunityComment==========");
		ResponseEntity response = null;
		if(commentService.updateCommunityComment(comment) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@DeleteMapping("/community/comment/{ccId}")
	@ApiOperation(value="커뮤니티 댓글 삭제")
	public Object deleteCommunityComment(@PathVariable int ccId) {
		logger.info("==========deleteCommunityComment==========");
		ResponseEntity response = null;
		if(commentService.deleteCommunityComment(ccId) == 1) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
