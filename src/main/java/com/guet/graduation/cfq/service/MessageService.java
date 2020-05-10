package com.guet.graduation.cfq.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理意见反馈
 * @author 123
 *
 */
public interface MessageService {

	/**
	 * 提交反馈信息到数据库中
	 * @param session
	 * @param request
	 */
	public void subFeedback(HttpSession session,HttpServletRequest request);
	
	/**
	 * 获取管理员的所有消息
	 * @param session
	 * @param map
	 */
	public void getAllMessage(HttpSession session, Map<String, Object> map);
	
	/**
	 * 回复用户反馈消息
	 * @param session
	 * @param request
	 */
	public void replyMessage(HttpSession session,HttpServletRequest request);
	
	/**
	 * 回复用户消息
	 * @param params
	 */
	public void replyMessage(JSONObject params, HttpSession session);
	
	/**
	 * 更新消息状态
	 * @param params
	 */
	public void updateMsgStatus(JSONObject params);
}
