package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 该类为“我的订单”界面需要显示的信息
 * @author 123
 *
 */
public class MyOrder {
	
	//申请编号
	private String orderId;
	//设备名
	private String equipmentName;
	//设备种类
	private String type;
	//订单的申请时间
	private Timestamp orderDate;
	//设备的使用日期
	Date useTime;
	//设备的使用时刻
	private String timeStamp;
	//设备的单价
	private double unitPrice;
	//订单的总价
	private double price;
	//订单的状态
	private String orderStatus;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public MyOrder(String orderId, String equipmentName, String type, Timestamp orderDate, Date useTime,
			String timeStamp, double unitPrice, double price, String orderStatus) {
		super();
		this.orderId = orderId;
		this.equipmentName = equipmentName;
		this.type = type;
		this.orderDate = orderDate;
		this.useTime = useTime;
		this.timeStamp = timeStamp;
		this.unitPrice = unitPrice;
		this.price = price;
		this.orderStatus = orderStatus;
	}
	
	public MyOrder() {
	}
	
	

}
