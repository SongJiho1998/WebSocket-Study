package com.kh.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// 일반클래스를 웹소켓 서버로 등록하기위한 방법
// @ServerEndpoint 어노테이션을 이용하면됨. -> 클래스 선언부에 선언하기.
@ServerEndpoint(value="/chatting.do")
public class ChattingServer {

	// 내부에 클라이언트가 요청하는 내용을 처리하는 메소드 등록
	private static Map<String , Session> clients = new HashMap<>(); // Session : 사용자마다 다른 세션값 가지고있음.
	
	@OnOpen
	public void open(Session session , EndpointConfig config) {
		// 접속을 요청한 클라이언트와 접속이 연결되면 실행되는 메소드.
		System.out.println("클라이언트 접속함");
		System.out.println(session.getId());
		clients.put(session.getId(), session);
	}
	
	// 클라이언트들이 보내는 메세지처리하는 메소드
	@OnMessage
	public void message(Session session , String msg) { // "발송자,수신자,메세지"
		// session 메세지를 보낸 클라이언트의 session객체
		// msg -> page에서 socket.send(데이터)
		System.out.println(msg);
		
		// 클라이언트가 보낸 메시지 파싱
		String[] data = msg.split(",");
		// 0번째 : 발송자
		// 1번째 : 수신자
		// 2번째 : 메세지
		
		// 보낸데이터 세션에 저장할수 있음.
		session.getUserProperties().put("msg", data);
		// 클라이언트가 보낸 데이터를 다른 접속한 클라이언트에게 전송하기.
		// 1. 접속한 클라이언트관리(클라이언트가 여러명일 수 있음)
		// 1) Collection클래스를 통해 : Map , List , Set을 이용해서 관리
		// 2) session클래스에서 접속한 모든 session을 알수있는 메소드 사용
	    //    session.getOpenSessions()
		//    -> 현재 웹소켓에 접속해서 session이 유지되고 있는 모든 session값을 반환해줌
		
		// 2. 접속 session객체를 이용해서 데이터를 전송할 수 있음.
		//    session.getBasicRemote() -> 접속한 세션과 연결되는 스트림을 가져옴
		
		// 3. getBasicRemote로 가져온 객체의 sendText()메소드 실행 -> 메세지를 클라이언트에게 전송
		Set<String> keys = clients.keySet();
		for(String key : keys) {
			
			Session s = (Session) clients.get(key);
			String[] clientData = (String[]) s.getUserProperties().get("msg");

			if (data[1].length() > 0) {
				if (clientData[0].equals(data[1]) || clientData[0].equals(data[0])) {
					try {
						s.getBasicRemote().sendText(msg); // 발송자,수신자,메세지
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else {
				try {
					s.getBasicRemote().sendText(msg); // 발송자,수신자,메세지
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			

		}
		
	}
	

}
