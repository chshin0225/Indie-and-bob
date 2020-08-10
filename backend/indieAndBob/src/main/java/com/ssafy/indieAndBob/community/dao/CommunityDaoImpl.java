package com.ssafy.indieAndBob.community.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.MyCommunitySearch;

@Repository
public class CommunityDaoImpl implements CommunityDao {
	@Autowired
	SqlSessionTemplate temp;
	
	private static String ns = "com.ssafy.indieAndBob.mapper.community.";
	
	@Override
	public int insertCommunity(Community community) {
		return temp.insert(ns + "insertCommunity", community);
	}

	@Override
	public List<Community> listCommunity(int page) {
		return temp.selectList(ns + "listCommunity", page);
	}

	@Override
	public Community communityDetail(int communityId) {
		return temp.selectOne(ns + "communityDetail", communityId);
	}

	@Override
	public int updateCommunity(Community community) {
		return temp.update(ns + "updateCommunity", community);
	}

	@Override
	public int deleteCommunity(int communityId) {
		return temp.delete(ns + "deleteCommunity", communityId);
	}

	@Override
	public List<Community> listMyCommunity(MyCommunitySearch search) {
		return temp.selectList(ns + "listMyCommunity", search);
	}

}
