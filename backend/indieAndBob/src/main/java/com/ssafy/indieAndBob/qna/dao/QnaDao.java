package com.ssafy.indieAndBob.qna.dao;

import java.util.List;

import com.ssafy.indieAndBob.qna.dto.QnaVO;

public interface QnaDao {
	public int create(QnaVO vo);
	public QnaVO read(Integer qna_id);
	public int update(QnaVO vo);
	public int delete(Integer qna_id);
	//public List<QnaVO> listAll();
}
