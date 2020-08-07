package com.ssafy.indieAndBob.qna.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.indieAndBob.qna.dto.QnaVO;

@Repository
public class QnaDaoImpl implements QnaDao {
	private static String ns = "com.ssafy.indieAndBob.mapper.qna.";
	
	@Autowired
	SqlSessionTemplate temp;
	
	public int create(QnaVO vo) {
		return temp.insert(ns + "create", vo);
	}
	
	public QnaVO read(Integer qna_id) {
		return temp.selectOne(ns + "read", qna_id);
	}
	
	public int update(QnaVO vo) {
		return temp.update(ns + "update", vo);
	}
	
	public int delete(Integer qna_id) {
		return temp.delete(ns + "delete", qna_id);
	}

	/*
	public List<QnaVO> listAll(){
		return temp.selectList(ns + "listAll");
	}
	*/
	
}
