//package com.ssafy.indieAndBob.alarm.controller;
//
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import lombok.extern.java.Log;
//
//@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
//@Log
//@Component
//@ServerEndpoint(value = "/alarm")
//public class AlarmController {
//
//	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
//	
//	private Session session;
//	public static Set<AlarmController> listeners = new CopyOnWriteArraySet<>();
//	private static int onlineCount = 0;
//	
//	@OnOpen
//	public void onOpen(Session session) {
//		onlineCount++;
//		this.session = session;
//		listeners.add(this);
//		log.info("onOpen called, userCount : " + onlineCount);
//	}
//	
//	@OnClose
//	public void onClose(Session session) {
//		onlineCount--;
//		listeners.remove(this);
//		log.info("onClose called, userCount:" + onlineCount);
//	}
//	
//}
