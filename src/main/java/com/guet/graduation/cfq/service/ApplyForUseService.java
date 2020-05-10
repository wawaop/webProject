package com.guet.graduation.cfq.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

public interface ApplyForUseService {

	/**
	 * 申请使用卡座——获取符合时间地点的卡座信息
	 * @param request 用于获取界面的各种参数——地点、时间
	 * @param map 将获取到的设备放置在map中，前端界面通过thymeleaf进行显示
	 * @return
	 */
	public String searchKaZuo(JSONObject params,Map<String, Object> map);
	
	public String searchKaZuo(HttpServletRequest request,Map<String, Object> map);
	
	/**
	 * 预定卡座：在apply_for_use表中插入数据
	 * @param params
	 * @return
	 */
	public String applyKaZuo(JSONObject params,HttpSession session);
	
	/**
	 * 获取“我的使用申请”并保存在map中
	 * @param session
	 * @param map
	 */
//	public void myUseApply(HttpSession session, Map<String, Object> map);
	public void myUseApply(HttpSession session, Map<String, Object> map, 
							Integer pageNum, Integer pageSize,
							Integer applyPageNum, Integer applyPageSize,
							Integer passPageNum, Integer passPageSize,
							Integer unPassPageNum, Integer unPassPageSize,
							Integer tabNum);
}
