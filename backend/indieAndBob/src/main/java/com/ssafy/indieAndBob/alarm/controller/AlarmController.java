//package com.ssafy.indieAndBob.alarm.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.indieAndBob.alarm.dto.Alarm;
//import com.ssafy.indieAndBob.alarm.service.AlarmService;
//import com.ssafy.indieAndBob.game.dto.Game;
//import com.ssafy.indieAndBob.response.dto.BasicResponse;
//
//import io.swagger.annotations.ApiOperation;
//
//@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
//@RestController
//public class AlarmController {
//
//	@Autowired
//	AlarmService alarmService;
//	
//	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
//	
//	@GetMapping("/alarm/{nickname}")
//	@ApiOperation(value="유저에게 온 알람 조회")
//	public Object selectAlarms(@PathVariable String nickname) {
//		logger.info("==========selectAlarms==========");
//		ResponseEntity response = null;
//		List<Alarm> alarms = alarmService.getAlarms(nickname);
//		if(alarms.size()>=0) {
//			final BasicResponse result = new BasicResponse();
//			result.status = true;
//			result.data = "success";
//			result.object = alarms;
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return response;
//	}
//	
//	@PostMapping("/alarm")
//	@ApiOperation(value="유저에게 알람 등록")
//	public Object insertAlarms(@RequestBody Alarm alarm) {
//		logger.info("==========insertAlarms==========");
//		ResponseEntity response = null;
//		if(alarmService.insertAlarm(alarm) == 1) {
//			final BasicResponse result = new BasicResponse();
//			result.status = true;
//			result.data = "success";
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		} else {
//			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return response;
//	}
//	
//}
