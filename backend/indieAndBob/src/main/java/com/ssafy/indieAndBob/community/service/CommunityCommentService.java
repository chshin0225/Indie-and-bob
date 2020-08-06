package com.ssafy.indieAndBob.community.service;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.CommunityComment;

public interface CommunityCommentService {
	int insertCommunityComment(CommunityComment comment);
	List<Community> listCommunityComment(int communityId);
	int updateCommunityComment(CommunityComment comment);
	int deleteCommunityComment(int ccId);
}
