package com.ssafy.indieAndBob.community.dao;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.CommunityComment;

public interface CommunityCommentDao {
	int insertCommunityComment(CommunityComment comment);
	List<Community> listCommunityComment(int communityId);
	int updateCommunityComment(CommunityComment comment);
	int deleteCommunityComment(int ccId);
}
