package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Order {

	//订单编号
	String order_id;
	//用户ID
	String userId;
	//设备编号
	String equipment_id;
	//提交预定的时间——申请使用的申请提交时间
	Timestamp order_date;
	//订单中使用的开始时刻
	Time star_time;
	//订单中使用的结束时刻
	Time end_time;
	//订单中使用的日期
	Date useTime;
	//订单总价
	double price;
	//订单状态——是否已付款
	String order_status;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(String equipment_id) {
		this.equipment_id = equipment_id;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public Time getStar_time() {
		return star_time;
	}
	public void setStar_time(Time star_time) {
		this.star_time = star_time;
	}
	public Time getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
	public Order(String order_id, String userId, String equipment_id, Timestamp order_date, Time star_time,
			Time end_time, Date useTime, double price, String order_status) {
		super();
		this.order_id = order_id;
		this.userId = userId;
		this.equipment_id = equipment_id;
		this.order_date = order_date;
		this.star_time = star_time;
		this.end_time = end_time;
		this.useTime = useTime;
		this.price = price;
		this.order_status = order_status;
	}
	
	public Order() {
	}
	
	
	
}
