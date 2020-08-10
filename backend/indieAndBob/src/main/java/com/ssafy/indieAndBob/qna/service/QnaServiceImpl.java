package com.ssafy.indieAndBob.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.qna.dao.QnaDao;
import com.ssafy.indieAndBob.qna.dto.QnaGameVO;
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
	public QnaVO read(int qna_id) {
		// TODO Auto-generated method stub
		return qnaDao.read(qna_id);
	}

	@Override
	public int update(QnaVO vo) {
		// TODO Auto-generated method stub
		return qnaDao.update(vo);
	}

	@Override
	public int delete(int qna_id) {
		// TODO Auto-generated method stub
		return qnaDao.delete(qna_id);
	}
	
	@Override
	public int updateAnswer(QnaVO qna_vo) {
		return qnaDao.updateAnswer(qna_vo);
	}

	@Override
	public boolean isDeveloper(QnaGameVO qna_game_vo) {
		// TODO Auto-generated method stub
		return qnaDao.isDeveloper(qna_game_vo);
	}

	@Override
	public List<QnaVO> readByGameIdNoSecret(int game_id) {
		// TODO Auto-generated method stub
		return qnaDao.readByGameIdNoSecret(game_id);
	}

	@Override
	public List<QnaVO> readByGameIdAllSecret(int gmae_id) {
		// TODO Auto-generated method stub
		return qnaDao.readByGameIdAllSecret(gmae_id);
	}

	@Override
	public List<QnaVO> readByGameIdMySecret(QnaGameVO qna_game_vo) {
		// TODO Auto-generated method stub
		return qnaDao.readByGameIdMySecret(qna_game_vo);
	}

	@Override
	public List<QnaVO> pagination(List<QnaVO> qnaList, int page) {
		int numOfContents = qnaList.size();
		int fromIndex = (page - 1) * 10;
		int toIndex = page * 10 - 1;
		
		qnaList.sort(null);
		if(toIndex > numOfContents) toIndex = numOfContents;
		qnaList = qnaList.subList(fromIndex, toIndex);
		qnaList.get(0).setNumOfContents(numOfContents);
		return qnaList;
	}
	
	
}
