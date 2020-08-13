package com.ssafy.indieAndBob.search.service;

import java.util.List;

import com.ssafy.indieAndBob.community.dto.Community;
import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.user.dto.User;

public interface SearchService {
	List<User> userSearch(String keyword);
	List<GameAll> gameSearch(String keyword);
	List<Community> communitySearch(String keyword);
}
