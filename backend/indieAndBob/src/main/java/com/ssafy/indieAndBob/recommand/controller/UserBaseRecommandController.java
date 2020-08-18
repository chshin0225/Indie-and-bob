package com.ssafy.indieAndBob.recommand.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.jwt.service.JwtService;
import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;
import com.ssafy.indieAndBob.recommand.service.UserbaseRecommandService;
import com.ssafy.indieAndBob.response.dto.BasicResponse;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class UserBaseRecommandController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserbaseRecommandService service;
	@Autowired
	JwtService jwtService;
	@Autowired
	GameService gameService;
	
	@GetMapping("/api/recommand/userbase")
	@ApiOperation(value = "유저기반 추천")
	public Object recommandUserbase(HttpServletRequest request) {
		logger.info("==========recommandUserbase==========");
		ResponseEntity response = null;
		String nickname = jwtService.getNickname(request);
//		String nickname = "우왁굳";
		int userId = service.getUserId(nickname);
		
		File file = new File("src/main/resources/static/recommand.txt");
		StringBuilder sb = new StringBuilder();
		List<UserbaseRecommand> list = service.userbaseRecommand();
		for(UserbaseRecommand recommand : list) {
			sb.append(recommand.getUserId()+","+recommand.getGameId()+",5\n");
		}
		
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(sb.toString());
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		List<GameAll> gamelist = new LinkedList<GameAll>();
		try {
			DataModel model = new FileDataModel(file);
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			List<RecommendedItem> recommendations = recommender.recommend(userId, 5);
			
			if(recommendations.size() == 0) {
				gamelist = service.randomRecommend();
			}
			else {
				logger.info("size : " + recommendations.size());
				for(RecommendedItem item : recommendations) {
					logger.info("item" + item);
					gamelist.add(gameService.selectGameById((int)item.getItemID()));
				}
			}
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = gamelist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e1) {
			gamelist = service.randomRecommend();
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = gamelist;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return response;
	}
}
