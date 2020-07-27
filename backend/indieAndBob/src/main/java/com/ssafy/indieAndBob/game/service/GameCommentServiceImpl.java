package com.ssafy.indieAndBob.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.game.dao.GameCommentDao;
import com.ssafy.indieAndBob.game.dto.GameComment;

@Service
public class GameCommentServiceImpl implements GameCommentService {
	@Autowired
	private GameCommentDao gameCommentDao;
	
	@Override
	public int registerComment(GameComment comment) {
		return gameCommentDao.registerComment(comment);
	}

}
