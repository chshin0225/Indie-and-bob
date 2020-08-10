package com.ssafy.indieAndBob.community.service;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.MyCommunitySearch;

public interface CommunityService {
	int insertCommunity(Community community);
	List<Community> listCommunity(int page);
	Community communityDetail(int communityId);
	int updateCommunity(Community community);
	int deleteCommunity(int communityId);
	List<Community> listMyCommunity(MyCommunitySearch search);
}
