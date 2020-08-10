package com.ssafy.indieAndBob.qna.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.qna.dto.QnaGameVO;
import com.ssafy.indieAndBob.qna.dto.QnaVO;

@Repository
public class QnaDaoImpl implements QnaDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.qna.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	@Override
	public int create(QnaVO vo) {
		return temp.insert(ns + "create", vo);
	}
	
	@Override
	public QnaVO read(int qna_id) {
		return temp.selectOne(ns + "read", qna_id);
	}
	@Override
	public int update(QnaVO vo) {
		return temp.update(ns + "update", vo);
	}
	@Override
	public int delete(int qna_id) {
		return temp.delete(ns + "delete", qna_id);
	}
	
	@Override
	public int updateAnswer(QnaVO qna_vo) {
		return temp.update(ns + "updateAnswer", qna_vo);
	}

	@Override
	public boolean isDeveloper(QnaGameVO qna_game_vo) {
		// TODO Auto-generated method stub
		QnaVO qna_vo = temp.selectOne(ns + "isDeveloper", qna_game_vo);
		if(qna_vo == null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<QnaVO> readByGameIdNoSecret(int game_id) {
		// TODO Auto-generated method stub
		return temp.selectList(ns + "readByGameIdNoSecret", game_id);
	}

	@Override
	public List<QnaVO> readByGameIdAllSecret(int game_id) {
		// TODO Auto-generated method stub
		return temp.selectList(ns + "readByGameIdAllSecret", game_id);
	}

	@Override
	public List<QnaVO> readByGameIdMySecret(QnaGameVO qna_game_vo) {
		// TODO Auto-generated method stub
		return temp.selectList(ns + "readByGameIdMySecret", qna_game_vo);
	}
}
