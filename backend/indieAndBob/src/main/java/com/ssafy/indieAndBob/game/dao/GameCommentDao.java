package com.ssafy.indieAndBob.game.dao;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.GameComment;

public interface GameCommentDao {
	int registerComment(GameComment comment);
	List<GameComment> selectCommentByGameId(String gameId);
	int deleteCommentByGcId(int gcId);
}
