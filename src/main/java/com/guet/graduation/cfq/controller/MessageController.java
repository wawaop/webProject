package com.guet.graduation.cfq.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	/**
	 * 从界面中获取意见信息，保存在服务器中
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/subFeedback")
	public String subFeedback(HttpSession session,HttpServletRequest request) {
		System.out.println("执行到了subFeedback");
		messageService.subFeedback(session, request);
		return "feedback.html";
	}
	
	/**
	 * 获取所有的信息，返回给管理员的消息处理界面
	 * @return
	 */
	@RequestMapping("/processingOpinion")
	public String processingOpinion(HttpSession session, Map<String, Object> map) {
		messageService.getAllMessage(session, map);
		return "ReplyMessage.html";
	}
	
	/**
	 * 回复消息
	 * @param session
	 * @param request
	 * @return
	 */
//	@RequestMapping("/replyMessage")
//	public String replyMessage(HttpSession session, HttpServletRequest request) {
//		System.out.println("执行到回复");
//		messageService.replyMessage(session, request);
//		return "ReplyMessage.html";
//	}
	
	@ResponseBody
	@RequestMapping("/replyMessage")
	public String replyMessage(@RequestBody JSONObject params, HttpSession session) {
		messageService.replyMessage(params, session);
		return "回复成功";
	}
	
	@RequestMapping("/message")
	public String myMessage(HttpSession session, Map<String, Object> map) {
		messageService.getAllMessage(session, map);
		return "myMessage.html";
	}
	
	@ResponseBody
	@RequestMapping("/updateMsgStatus")
	public String updateMsgStatus(@RequestBody JSONObject params) {
		//
//		System.out.println("得到的消息ID为："+params.getString("messageId"));
		messageService.updateMsgStatus(params);
		return "更新成功";
	}

}
