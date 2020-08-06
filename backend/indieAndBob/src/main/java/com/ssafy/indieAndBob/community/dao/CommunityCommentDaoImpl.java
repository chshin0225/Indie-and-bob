package com.ssafy.indieAndBob.community.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.CommunityComment;

@Repository
public class CommunityCommentDaoImpl implements CommunityCommentDao{
	
	@Autowired
	SqlSessionTemplate temp;
	
	private static String ns = "com.ssafy.indieAndBob.mapper.communitycomment.";

	@Override
	public int insertCommunityComment(CommunityComment comment) {
		return temp.insert(ns + "insertCommunityComment", comment);
	}

	@Override
	public List<Community> listCommunityComment(int communityId) {
		return temp.selectList(ns + "listCommunityComment", communityId);
	}

	@Override
	public int updateCommunityComment(CommunityComment comment) {
		return temp.update(ns + "updateCommunityComment", comment);
	}

	@Override
	public int deleteCommunityComment(int ccId) {
		return temp.delete(ns + "deleteCommunityComment", ccId);
	}
	
	

}
