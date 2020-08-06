package com.ssafy.indieAndBob.qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.qna.dao.QnaDao;
import com.ssafy.indieAndBob.qna.dto.QnaVO;

@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	QnaDao qnaDao;

	@Override
	public int create(QnaVO vo) {
		// TODO Auto-generated method stub
		return qnaDao.create(vo);
	}

	@Override
	public QnaVO read(Integer qna_id) {
		// TODO Auto-generated method stub
		return qnaDao.read(qna_id);
	}

	@Override
	public int update(QnaVO vo) {
		// TODO Auto-generated method stub
		return qnaDao.update(vo);
	}

	@Override
	public int delete(Integer qna_id) {
		// TODO Auto-generated method stub
		return qnaDao.delete(qna_id);
	}
	
}
