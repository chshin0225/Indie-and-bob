package com.ssafy.indieAndBob.qna.service;

import java.util.List;

import com.ssafy.indieAndBob.qna.dto.QnaGameVO;
import com.ssafy.indieAndBob.qna.dto.QnaVO;

public interface QnaService {
	public int create(QnaVO vo);
	public QnaVO read(int qna_id);
	public int update(QnaVO vo);
	public int delete(int qna_id);
	public boolean isDeveloper(QnaGameVO qna_game_vo);
	public List<QnaVO> readByGameIdNoSecret(int game_id);
	public List<QnaVO> readByGameIdAllSecret(int gmae_id);
	public List<QnaVO> readByGameIdMySecret(QnaGameVO qna_game_vo);
}
