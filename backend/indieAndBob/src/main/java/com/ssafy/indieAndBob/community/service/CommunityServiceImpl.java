package com.ssafy.indieAndBob.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.community.dao.CommunityDao;
import com.ssafy.indieAndBob.community.dto.Community;

@Service
public class CommunityServiceImpl implements CommunityService {
	
	@Autowired
	CommunityDao communityDao;
	
	@Override
	public int insertCommunity(Community community) {
		return communityDao.insertCommunity(community);
	}

	@Override
	public List<Community> listCommunity() {
		return communityDao.listCommunity();
	}

	@Override
	public Community communityDetail(int communityId) {
		return communityDao.communityDetail(communityId);
	}

	@Override
	public int updateCommunity(Community community) {
		return communityDao.updateCommunity(community);
	}

	@Override
	public int deleteCommunity(int communityId) {
		return communityDao.deleteCommunity(communityId);
	}

}