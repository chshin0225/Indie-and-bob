package com.ssafy.indieAndBob.community.service;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;

public interface CommunityService {
	int insertCommunity(Community community);
	List<Community> listCommunity();
	Community communityDetail(int communityId);
	int updateCommunity(Community community);
	int deleteCommunity(int communityId);
}
