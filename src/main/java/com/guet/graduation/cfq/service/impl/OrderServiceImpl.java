package com.guet.graduation.cfq.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.dao.OrderMapper;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.MyOrder;
import com.guet.graduation.cfq.entity.Order;
import com.guet.graduation.cfq.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Override
	public String getMyOrders(HttpSession session, Map<String, Object> map) {
//		//获取用户ID
//		String userId= (String) session.getAttribute("userId");
//		//根据用户ID查找order表，得到属于该用户的订单
//		List<Order> orders=orderMapper.getOrderByUserId(userId);
//		//根据得到的订单表的信息，构建“我的订单”界面显示的数据
//		List<MyOrder> myOrders=getMyOrderInfo(orders);
//		//将显示信息放置在map中
//		map.put("myOrders", myOrders);
//		
//		//获取“待支付”的订单
//		
		return "查找成功";
	}

	/**
	 * 构建“我的订单”界面需要的信息
	 * @param orders
	 * @return
	 */
	private List<MyOrder> getMyOrderInfo(List<Order> orders) {
		//界面需要的订单信息的List
		List<MyOrder> myOrders=new ArrayList<MyOrder>();
		//遍历从数据库中得到的订单，依次将其转换构造界面展示需要的信息
		for(Order order : orders) {
			//申请编号
			String orderId=order.getOrder_id();
			
			//获取设备信息
			String equipmentId=order.getEquipment_id();
			Equipment equipment=equipmentMapper.getEquipmentById(equipmentId);
			//设备名
			String equipmentName=equipment.getEquipmentName();
			//设备种类
			String type=equipment.getType();
			
			//订单的申请时间
			Timestamp orderDate=order.getOrder_date();
			
			//订单的使用日期
			Date useTime=order.getUseTime();
			
			//订单的使用时刻
			String timeStamp=order.getStar_time().toString()+" - "+order.getEnd_time();
			
			//设备的单价
			double unitPrice=equipment.getPrice();
			
			//订单的总价
			double price=order.getPrice();
			
			//订单的状态
			String orderStatus=order.getOrder_status();
			
			//构建界面信息
			MyOrder myOrder=new MyOrder(orderId, equipmentName, type, orderDate, useTime, timeStamp, unitPrice, price, orderStatus);
			
			//插入List中
			myOrders.add(myOrder);
		}
		return myOrders;
	}

	@Override
	public String payOrder(JSONObject params, Map<String, Object> map) {
		//支付——将订单状态修改为“已支付”
		String orderId=params.getString("payID");
		//更新订单状态
		orderMapper.updateOrderStatus(orderId);
		
		return "成功";
	}

	@Override
	public String getMyOrdersWithPage(HttpSession session, Map<String, Object> map, Integer pageNum, Integer pageSize,
									Integer unPaidPageNum, Integer unPaidPageSize, Integer paidPageNum, Integer paidPageSize,
									Integer tabNum) {
	    //为了程序的严谨性，判断非空：
	    if(pageNum == null){
	        pageNum = 1;   //设置默认当前页
	    }
	    if(pageNum <= 0){
	        pageNum = 1;
	    }
	    if(pageSize == null){
	        pageSize = 8;    //设置默认每页显示的数据数
	    }
	    System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);
		//获取用户ID
		String userId= (String) session.getAttribute("userId");
	    try {
	        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
	    	PageHelper.startPage(pageNum,pageSize);
	        List<MyOrder> myOrders = orderMapper.getOrderByUserId(userId);
//	        System.out.println("分页数据："+myOrders);
	        PageInfo<MyOrder> pageInfo = new PageInfo<MyOrder>(myOrders,pageSize);
	        //4.使用map带回前端
	        map.put("pageInfo", pageInfo);
	        
	        //处理“待支付”tab的内容
	        PageHelper.startPage(unPaidPageNum,unPaidPageSize);
	        List<MyOrder> toBePaidOrders=orderMapper.getTobePaidOrderByUserId(userId);
	        PageInfo<MyOrder> toBePaidOrdesPageInfo=new PageInfo<MyOrder>(toBePaidOrders,unPaidPageSize);
	        //放到map中
	        map.put("toBePaidOrders", toBePaidOrdesPageInfo);
	        
	        //处理“已支付”tab的内容
	        PageHelper.startPage(paidPageNum, paidPageSize);
	        List<MyOrder> paidOrders=orderMapper.getPaidOrders(userId);
	        PageInfo<MyOrder> paidOrdersInfo=new PageInfo<MyOrder>(paidOrders,paidPageSize);
	        //放到map中
	        map.put("paidOrders", paidOrdersInfo);
	        
	    }finally {
	        PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
	    }
	    //tab位置
	    map.put("tabNum", tabNum);
		return null;
	}

}
