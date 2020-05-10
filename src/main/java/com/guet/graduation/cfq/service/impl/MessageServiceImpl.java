package com.guet.graduation.cfq.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.dao.MessageMapper;
import com.guet.graduation.cfq.entity.Message;
import com.guet.graduation.cfq.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageMapper;

	@Override
	public void subFeedback(HttpSession session, HttpServletRequest request) {
		// messageID
		String messageId = UUID.randomUUID().toString().replaceAll("-", "");
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		// 接收者ID
		String reciveId = "c9d7942b6bca4d54b78316de268bbba4";
		// 发送时间
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp send_time = new Timestamp(currentTime.getTime());
		// 提交信息
		String title = request.getParameter("title");
		if (title == null) {
			return;
		}
		String content = request.getParameter("content");
		// 保存到数据库
		Message message = new Message(messageId, userId, reciveId, send_time, title, content, "未读");
		messageMapper.insertMessage(message);
	}

	/**
	 * 获取管理员的所有消息
	 */
	@Override
	public void getAllMessage(HttpSession session, Map<String, Object> map) {
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		List<Message> messages = messageMapper.getAllMessage(userId);
		map.put("messages", messages);
	}

	/**
	 * 回复用户反馈消息
	 */
	@Override
	public void replyMessage(HttpSession session, HttpServletRequest request) {
		//将当前消息状态更新为已读
		String oldMessageId=request.getParameter("messageId");
		//更新消息状态
		
		//回复消息
		// messageID
		String messageId = UUID.randomUUID().toString().replaceAll("-", "");
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		// 接收者ID
		String receiveId = request.getParameter("replyUserId");
		// 发送时间
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp send_time = new Timestamp(currentTime.getTime());
		//messageTitle
		String title = request.getParameter("title");
		title="关于——"+title+"——问题的回复";
		String content = request.getParameter("content");
		Message message=new Message(messageId, userId, receiveId, send_time, title, content, "未读");
		messageMapper.insertMessage(message);
	}

	@Override
	public void updateMsgStatus(JSONObject params) {
		//获取消息ID
		String messageId=params.getString("messageId");
		//更新消息状态
		messageMapper.updateMsgStatus(messageId);
	}

	@Override
	public void replyMessage(JSONObject params, HttpSession session) {
		//获取消息ID和回复内容
		String msgId=params.getString("msgId");
		String msgContent=params.getString("msgContent");
		//获取回复对象ID和回复标题
		Message message=messageMapper.getMessageById(msgId);
		String replyUserId=message.getSend_userId();
		String replyTitle="关于您【"+message.getMessage_title()+"】反馈的回复";
		//插入数据库
		String message_id=UUID.randomUUID().toString().replaceAll("-", "");	//新建的消息的ID
		String send_userId=(String) session.getAttribute("userId");	//发送者ID
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp send_time = new Timestamp(currentTime.getTime());	//发送时间
		Message message2=new Message(message_id, send_userId, replyUserId, send_time, replyTitle, msgContent, "未读");
		messageMapper.insertMessage(message2);
	}

}
