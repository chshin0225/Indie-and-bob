package com.ssafy.indieAndBob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.indieAndBob.game.service.GameService;

import lombok.extern.java.Log;

@Component
@Log
public class ScheduledTasks {
	
	@Autowired
	GameService service;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void endDay() {
		log.info("end Day");
		service.endDay();
	}
}
