package com.guet.graduation.cfq.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.coyote.http11.filters.VoidInputFilter;

import com.alibaba.fastjson.JSONObject;

public interface ApplyService {
	
	public void getPublisApply(Map<String, Object> map);

	public void getDetailApplyById(String applyId,Map<String,Object> map);
	
	public void passApply(JSONObject params);
	
	public void unPassApply(JSONObject params);
	
	/**
	 * 处理"我的设备发布申请"界面的内容——获取我的发布申请并显示
	 * @param session
	 * @param map
	 */
	public void myPublishApply(HttpSession session, Map<String,Object> map);
	
	/**
	 * 处理“我的设备发布申请”界面的内容——分页处理
	 * @param session
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @param applyPageNum
	 * @param applyPageSize
	 * @param passPageNum
	 * @param passPageSize
	 * @param unPassPageNum
	 * @param unPassPageSize
	 * @param tabNum
	 */
	public void myPublishApply(HttpSession session, Map<String, Object> map, 
			Integer pageNum, Integer pageSize,
			Integer applyPageNum, Integer applyPageSize,
			Integer passPageNum, Integer passPageSize,
			Integer unPassPageNum, Integer unPassPageSize,
			Integer tabNum);
}
