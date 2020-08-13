package com.ssafy.indieAndBob.search.dao;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.user.dto.User;

public interface SearchDao {
	List<User> userSearch(String keyword);
	List<GameAll> gameSearch(String keyword);
	List<Community> communitySearch(String keyword);
}
