package com.ssafy.indieAndBob.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.community.dao.CommunityCommentDao;
import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.community.dto.CommunityComment;

@Service
public class CommunityCommentServiceImpl implements CommunityCommentService {

	@Autowired
	CommunityCommentDao commentDao;
	@Override
	public int insertCommunityComment(CommunityComment comment) {
		return commentDao.insertCommunityComment(comment);
	}

	@Override
	public List<Community> listCommunityComment(int communityId) {
		return commentDao.listCommunityComment(communityId);
	}

	@Override
	public int updateCommunityComment(CommunityComment comment) {
		return commentDao.updateCommunityComment(comment);
	}

	@Override
	public int deleteCommunityComment(int ccId) {
		return commentDao.deleteCommunityComment(ccId);
	}

}
