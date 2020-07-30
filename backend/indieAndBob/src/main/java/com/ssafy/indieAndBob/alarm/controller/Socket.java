package com.ssafy.indieAndBob.alarm.controller;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ssafy.indieAndBob.alarm.dto.Alarm;
import com.ssafy.indieAndBob.alarm.service.AlarmService;

import lombok.extern.java.Log;

@Log
@Component
@ServerEndpoint(value = "/websocket")  //서버가 바인딩된 주소를 뜻함.
public class Socket {
    private Session session;
    public static Map<String, Socket> users = new ConcurrentHashMap<>();
    public static Map<String, String> sessionIds = new ConcurrentHashMap<>();
    
    @Autowired
    AlarmService alarmService;

    @OnOpen //클라이언트가 소켓에 연결되때 마다 호출
    public void onOpen(Session session) {
        this.session = session;
        log.info("id : " + session.getId());
        log.info("onOpen called, userCount:" + users.size());
    }

    @OnClose //클라이언트와 소켓과의 연결이 닫힐때 (끊길떄) 마다 호
    public void onClose(Session session) {
        String nickname = sessionIds.get(session.getId());
        sessionIds.remove(session.getId());
        users.remove(nickname);
        log.info("onClose called, userCount:" + users.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("onMessage called, message:" + message + ", " + session.getId());
        String[] request = message.split(",");
        if(request[0].equals("login")) {
            users.put(request[1], this);
            sessionIds.put(session.getId(), request[1]);
            log.info("onOpen called, userCount:"+ users.size());
            List<Alarm> alarms = alarmService.getAlarms(request[1]);
            for(Alarm alarm : alarms) {
            	String msg = alarm.getType()+","+alarm.getNickname()+","+alarm.getTarget();
            	this.sendMessage(msg);
            }
        }
        else if(request[0].equals("follow")){
        	String nickname = request[1];
        	String target = request[2];
        	Socket socket = users.get(target);
        	if(socket != null) {
        		socket.sendMessage(message);
        	}
        	else {
        		Alarm alarm = new Alarm();
        		alarm.setType("follow");
        		alarm.setNickname(nickname);
        		alarm.setTarget(target);
        		alarmService.insertAlarm(alarm);
        	}
        }
    }

    @OnError //의도치 않은 에러 발생
    public void onError(Session session, Throwable throwable) {
    	String nickname = sessionIds.get(session.getId());
        sessionIds.remove(session.getId());
        users.remove(nickname);
        log.info("onClose called, userCount:" + users.size());
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.warning("Caught exception while sending message to Session " + this.session.getId() + "error:" + e.getMessage());
        }
    }
}