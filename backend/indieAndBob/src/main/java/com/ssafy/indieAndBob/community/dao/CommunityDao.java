package com.ssafy.indieAndBob.community.dao;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;

public interface CommunityDao {
	int insertCommunity(Community community);
	List<Community> listCommunity();
	Community communityDetail(int communityId);
	int updateCommunity(Community community);
	int deleteCommunity(int communityId);
}
