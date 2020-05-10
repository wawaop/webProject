package com.guet.graduation.cfq.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

public interface OrderService {
	
	/**
	 * 获取我的订单
	 * @param session
	 * @param map
	 * @return
	 */
	public String getMyOrders(HttpSession session,Map<String, Object> map);
	
	/**
	 * 获取“我的订单”——进行分页处理
	 * @param session
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getMyOrdersWithPage(HttpSession session, Map<String, Object> map, Integer pageNum, Integer pageSize,
									Integer unPaidPageNum, Integer unPaidPageSize, Integer paidPageNum, Integer paidPageSize,
									Integer tabNum);
	
	/**
	 * 支付
	 * @return
	 */
	public String payOrder(JSONObject params, Map<String, Object> map);

}
