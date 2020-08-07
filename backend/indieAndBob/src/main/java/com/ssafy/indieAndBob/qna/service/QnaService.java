package com.ssafy.indieAndBob.qna.service;

import java.util.List;

import com.ssafy.indieAndBob.qna.dto.QnaVO;

public interface QnaService {
	public int create(QnaVO vo);
	public QnaVO read(Integer qna_id);
	public int update(QnaVO vo);
	public int delete(Integer qna_id);
}
