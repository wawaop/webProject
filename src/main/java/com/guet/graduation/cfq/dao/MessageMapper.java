package com.guet.graduation.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.Message;

@Mapper
public interface MessageMapper {
	
	/**
	 * 向数据库中插入消息
	 * @param message
	 */
	public void insertMessage(Message message);
	
	/**
	 * 获取用户的所有消息
	 * @param userId
	 * @return
	 */
	public List<Message> getAllMessage(String userId);
	
	/**
	 * 更新消息状态
	 * @param msgId
	 */
	public void updateMsgStatus(String msgId);
	
	/**
	 * 通过消息ID获取对应信息
	 * @param msgId
	 * @return
	 */
	public Message getMessageById(String msgId);

}
