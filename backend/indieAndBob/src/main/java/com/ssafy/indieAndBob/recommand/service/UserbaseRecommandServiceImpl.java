package com.ssafy.indieAndBob.recommand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.indieAndBob.recommand.dao.UserBaseRecommandDao;
import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;

@Service
public class UserbaseRecommandServiceImpl implements UserbaseRecommandService {

	@Autowired
	UserBaseRecommandDao ubDao;
	
	@Override
	public List<UserbaseRecommand> userbaseRecommand() {
		return ubDao.userbaseRecommand();
	}

	@Override
	public int getUserId(String nickname) {
		return ubDao.getUserId(nickname);
	}
	
}
