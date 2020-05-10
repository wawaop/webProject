package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Message {
	String message_id;
	
	String send_userId;
	
	String receive_userId;
	
	Timestamp send_time;
	
	String message_title;
	
	String message_content;
	
	String status;

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getSend_userId() {
		return send_userId;
	}

	public void setSend_userId(String send_userId) {
		this.send_userId = send_userId;
	}

	public String getReceive_userId() {
		return receive_userId;
	}

	public void setReceive_userId(String receive_userId) {
		this.receive_userId = receive_userId;
	}

	public Timestamp getSend_time() {
		return send_time;
	}

	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}

	public String getMessage_title() {
		return message_title;
	}

	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Message(String message_id, String send_userId, String receive_userId, Timestamp send_time, String message_title,
			String message_content, String status) {
		super();
		this.message_id = message_id;
		this.send_userId = send_userId;
		this.receive_userId = receive_userId;
		this.send_time = send_time;
		this.message_title = message_title;
		this.message_content = message_content;
		this.status = status;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
