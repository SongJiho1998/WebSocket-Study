<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
	
	<input type="text" id="sender" size="10" placeholder="보내는이름">
	<input type="text" id="receiver" size="10" placeholder="받는사람이름">
	<input type="text" id="msg" placeholder="전송할 메세지입력">
	<button onclick="sendMsg();">전송</button>
	
	<div id="msgContainer">
		
	</div>
	
	<script>
		// 웹소켓 서버에 연결하기
		// WebSocket객체를 생성하기 
		// const socket = new WebSocket("ws://192.168.120.58:8001/<%=request.getContextPath()%>/chatting.do");
		const socket = new WebSocket("ws://192.168.120.58:8001/<%=request.getContextPath()%>/chatting2.do");
		// htpps://localhost:8001/~
		// http -> ws:주소
		// https -> wss:주소
		
		// socket설정하기
		// 1. 접속후 실행되는 이벤트핸들러 등록하기
		socket.onopen = function(e){ // 소켓이 열렸을때 실행되는 함수
			console.log("웹 소켓에 접속 성공.");
			console.log(e);
		}
		
		// 2. 웹소켓 서버에서 sendText , sendObject메소드를 실행하면 실행되는 함수
		socket.onmessage = function(e){
			console.log("메세지 수신");
			// 수신된 데이터를 받으려면 이벤트객체(e)의 data속성을 이용함.
			console.log(e);
			console.log(e.data);
			// Object형태의 String데이터를 객체로 변환해주기.
			// JSON.parse(e.data);
			console.log(JSON.parse(e.data));
			
			let msg = JSON.parse(e.data);
			if(msg["sender"] == $("#sender").val()){
				$("#msgContainer").append($("<p>").text("<"+msg["sender"]+"> "+msg["msg"]).css("text-align","left"));
			}else{
				$("#msgContainer").append($("<p>").text("<"+msg["sender"]+"> "+msg["msg"]).css("text-align","right"));
				
			}
			
// 			let msg = e.data.split(",") // 
// 			if(msg[0] == $("#sender").val()){
// 				$("#msgContainer").append($("<p>").text("<"+msg[0]+">"+msg[2]).css("text-align","left")); //[0] : 발신자 / [1] : 수신자 / [2] : 메세지
// 			}else{
// 				$("#msgContainer").append($("<p>").text("<"+msg[1]+">"+msg[2]).css("text-align","right"));
// 			}
		}
		
		// 3. 웹소켓서버에서 메세지를 전송하는 함수.
		const sendMsg = () => {
			// 전송할 메세지 전처리
			// 전처리한 메세지를 전송하는 방법 : socket.send(데이터);
			// 발송자,수신자,메세지내용
			// socket.send($("#sender").val()+","+$("#receiver").val()+","+$("#msg").val());
			
// 			{sender : $("#sender").val(), // 이 방법으로도 가능은하나 가독성이 안좋음.
// 			 receiver : $("#receiver").val(),
// 			 msg      : $("#msg").val()}

			let msg = new Message($("#sender").val() , $("#receiver").val() , $("#msg").val());
			console.log(msg);
			
			// JSON.stringify(Object)함수를 이용해서 객체를 JSON오브젝트로 변환시킴
			console.log(JSON.stringify(msg));
			socket.send(JSON.stringify(msg)); // JSON형태의 String타입으로 변경되서 출력.
 		};
		
		function Message(sender , receiver , msg){
			// this = {}
			
			this.sender = sender;
			this.receiver = receiver;
			this.msg = msg;
			
			// return this -> 자동으로 실행됨.
		}
		
	</script>
	
	
</body>
</html>