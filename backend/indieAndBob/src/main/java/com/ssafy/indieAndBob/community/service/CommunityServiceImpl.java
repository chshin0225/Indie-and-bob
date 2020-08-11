package com.ssafy.indieAndBob.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.community.dao.CommunityDao;
import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.MyCommunitySearch;

@Service
public class CommunityServiceImpl implements CommunityService {
	
	@Autowired
	CommunityDao communityDao;
	
	@Override
	public int insertCommunity(Community community) {
		return communityDao.insertCommunity(community);
	}

	@Override
	public List<Community> listCommunity(int page) {
		return communityDao.listCommunity(page);
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

	@Override
	public List<Community> listMyCommunity(MyCommunitySearch search) {
		return communityDao.listMyCommunity(search);
	}

	@Override
	public int listCommunityCount() {
		return communityDao.listCommunityCount();
	}

	@Override
	public int listMyCommunityCount(String nickname) {
		return communityDao.listMyCommunityCount(nickname);
	}

}
