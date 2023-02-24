package com.kh.websocket;

import java.io.IOException;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.kh.websocket.model.vo.Message;

@ServerEndpoint(value="/chatting2.do" , 
			    decoders= {JsonDecoder.class} , 
			    encoders= {JsonEncoder.class})

public class ChattingServer2 {

	@OnOpen
	public void open(Session session , EndpointConfig config) {
		System.out.println("클라이언트 접속함.");
		System.out.println(session.getId());
	}
	
	@OnMessage
	public void message(Session session , Message msg) {
		
		System.out.println(msg);
		// 클라이언트가 보낸 메세지 파싱하기.
		// Message date = new Gson().fromJson(msg , Message.class); 
		
		session.getUserProperties().put("msg", msg);
		
		Set<Session> clients = session.getOpenSessions();
		for(Session s : clients) {
			Message clientData = (Message) s.getUserProperties().get("msg");
			
			if (clientData != null) {
				if (clientData.getReceiver() != null && !clientData.getReceiver().equals("")) { // 아예 작성을 안하거나 빈값으로 넘어온
																								// 경우가
					// 아닐 경우.
					if (clientData.getSender().equals(msg.getReceiver())
							|| clientData.getSender().equals(msg.getSender())) {
						try {
							s.getBasicRemote().sendObject(msg);
						} catch (IOException | EncodeException e) {
							e.printStackTrace();
						}
					}
				} else {
					try {
						s.getBasicRemote().sendObject(msg);
					} catch (IOException | EncodeException e) {
						e.printStackTrace();
					}

				}
			}
		}

	}
	


}
