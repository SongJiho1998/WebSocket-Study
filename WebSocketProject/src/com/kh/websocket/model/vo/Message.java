package com.kh.websocket.model.vo;

public class Message {
	
	private String sender; // 발송자
	private String receiver; // 수신자
	private String msg; // 사용자가 보내는 메세지

	public Message() {
		
	}

	public Message(String sender, String receiver, String msg) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.msg = msg;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", receiver=" + receiver + ", msg=" + msg + "]";
	}
	
	

}
