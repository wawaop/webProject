package com.guet.graduation.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.EquipmentIdAndTime;
import com.guet.graduation.cfq.entity.EquipmentIdAndTimes;
import com.guet.graduation.cfq.entity.MyOrder;
import com.guet.graduation.cfq.entity.Order;

@Mapper
public interface OrderMapper {
	
	/**
	 * 将订单信息插入到订单表中
	 * @param order
	 */
	public void insertOrder(Order order);
	
	/**
	 * 根据用户ID获取属于他的所有订单
	 * @param userId
	 * @return
	 */
	public List<MyOrder> getOrderByUserId(String userId);

	/**
	 * 根据设备ID和日期（一天）来查找使用的总价
	 * @param equipmentIdAndTime
	 * @return
	 */
	public Double getSumPriceByEIdAndTime(EquipmentIdAndTime equipmentIdAndTime);
	
	/**
	 * 获取时间段内该设备收入的总价
	 * @param equipmentIdAndTimes
	 * @return
	 */
	public Float getSumByEidAndTimes(EquipmentIdAndTimes equipmentIdAndTimes);
	
	/**
	 * 更新订单状态为“已支付”
	 * @param orderId
	 */
	public void updateOrderStatus(String orderId);
	
	/**
	 * 获取该用户的“待支付”订单
	 * @param userId
	 * @return
	 */
	public List<MyOrder> getTobePaidOrderByUserId(String userId);
	
	/**
	 * 获取该用户“已支付”的订单
	 * @param userId
	 * @return
	 */
	public List<MyOrder> getPaidOrders(String userId);
}
