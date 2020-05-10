package com.guet.graduation.cfq.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 获取我的订单,放置到map中,跳转界面到myOrder.html
	 * @param session
	 * @param map
	 * @return
	 */
//	@RequestMapping("/myOrders")
//	public String getMyOrder(HttpSession session,Map<String,Object> map) {
//		//获取我的订单放置在map中
//		orderService.getMyOrders(session, map);
//		//跳转界面到myOrder.html
//		return "myOrder.html";
//	}
	
	/**
	 * 获取“我的订单”——进行分页处理的“我的订单”界面
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/myOrders")
	public String getMyOrderWithPage(HttpSession session, Map<String, Object> map,@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
            						@RequestParam(defaultValue="8",value="pageSize")Integer pageSize,
            						@RequestParam(required = false,defaultValue="1",value="unPaidPageNum")Integer unPaidPageNum,
            						@RequestParam(defaultValue="8",value="unPaidPageSize")Integer unPaidPageSize,
            						@RequestParam(required = false,defaultValue="1",value="paidPageNum")Integer paidPageNum,
            						@RequestParam(defaultValue="8",value="paidPageSize")Integer paidPageSize,
            						@RequestParam(defaultValue="1",value="tabNum")Integer tabNum) {
		orderService.getMyOrdersWithPage(session, map, pageNum, pageSize, unPaidPageNum,unPaidPageSize, paidPageNum, paidPageSize, tabNum);
		return "myOrder.html";
	}
	
	/**
	 * 支付
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/payOrder")
	public String payOrder(@RequestBody JSONObject params, Map<String, Object> map) {
		System.out.println("得到的支付ID为："+params.getString("payID"));
		orderService.payOrder(params,map);
		return "成功";
	}

}
