package com.ssafy.indieAndBob.recommand.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.indieAndBob.recommand.dto.UserbaseRecommand;
import com.ssafy.indieAndBob.recommand.service.UserbaseRecommandService;
import com.ssafy.indieAndBob.user.controller.UserController;

import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@RestController
public class UserBaseRecommandController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserbaseRecommandService service;
	
	@GetMapping("/api/recommand/userbase")
	@ApiOperation(value = "유저기반 추천")
	public Object recommandUserbase() {
		logger.info("==========recommandUserbase==========");
		ResponseEntity response = null;
		
		String path = UserBaseRecommandController.class.getResource("").getPath();
		System.out.println(path);
		
		File file = new File(path + "recommand.txt");
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
		
		try{
	        //파일 객체 생성
	        File file2 = new File("C:/Users/multicampus/Desktop/dataset.txt");
	        //입력 스트림 생성
	        FileReader filereader = new FileReader(file);
	        int singleCh = 0;
	        while((singleCh = filereader.read()) != -1){
	            System.out.print((char)singleCh);
	        }
	        filereader.close();
	    }catch (FileNotFoundException e) {
	        // TODO: handle exception
	    }catch(IOException e){
	        System.out.println(e);
	    }
		
		try {
			DataModel model = new FileDataModel(file);
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			List<RecommendedItem> recommendations = recommender.recommend(0, 1);
			logger.info("size : " + recommendations.size());
			for(RecommendedItem item : recommendations) {
				logger.info("item" + item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return response;
	}
}
