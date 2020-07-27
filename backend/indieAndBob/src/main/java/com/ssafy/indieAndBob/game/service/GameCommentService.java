package com.ssafy.indieAndBob.game.service;

import java.util.List;

import com.ssafy.indieAndBob.game.dto.GameComment;

public interface GameCommentService {
	int registerComment(GameComment comment);
	List<GameComment> selectCommentByGameId(String gameId);
}
