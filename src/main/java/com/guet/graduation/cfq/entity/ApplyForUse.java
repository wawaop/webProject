package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ApplyForUse {

	//申请使用的编号
	String apply_id;
	//所申请的设备编号
	String equipment_id;
	//所申请的用户ID
	String userId;
	//申请提出的时间
	Timestamp apply_date;
	//当前申请的状态
	String apply_status;
	//申请使用的开始时刻
	Time star_time;
	//申请使用的结束时刻
	Time end_time;
	//申请使用的日期
	Date useTime;
	//申请原因
	String applyReason;
	
	//其他的,使用申请处理那里需要返回的值
	String equipmentName;
	//设备类型
	String type;
	//单价
	Float price;
	//用户名
	String login;
	//总价
	Float totalPrice;
	
	//标志位——判断是否为最优解，1为最优解
	int flag=0;
	
	//申请的使用时刻（数据库直接返回字符串类型）
	String userTimestap;
	
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}
	public String getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(String equipment_id) {
		this.equipment_id = equipment_id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getApply_date() {
		return apply_date;
	}
	public void setApply_date(Timestamp apply_date) {
		this.apply_date = apply_date;
	}
	public String getApply_status() {
		return apply_status;
	}
	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
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
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public String getUserTimestap() {
		return userTimestap;
	}
	public void setUserTimestap(String userTimestap) {
		this.userTimestap = userTimestap;
	}
	public ApplyForUse(String apply_id, String equipment_id, String userId, Timestamp apply_date, String apply_status,
			Time star_time, Time end_time, Date useTime, String applyReason) {
		super();
		this.apply_id = apply_id;
		this.equipment_id = equipment_id;
		this.userId = userId;
		this.apply_date = apply_date;
		this.apply_status = apply_status;
		this.star_time = star_time;
		this.end_time = end_time;
		this.useTime = useTime;
		this.applyReason = applyReason;
	}
	
	public ApplyForUse(String equipment_id, Date useTime, String apply_status) {
		this.equipment_id = equipment_id;
		this.useTime = useTime;
		this.apply_status=apply_status;
	}
	
	
	public ApplyForUse(String apply_id, Timestamp apply_date, String apply_status, Date useTime, String equipmentName,
			String type, Float price, String userTimestap) {
		super();
		this.apply_id = apply_id;
		this.apply_date = apply_date;
		this.apply_status = apply_status;
		this.useTime = useTime;
		this.equipmentName = equipmentName;
		this.type = type;
		this.price = price;
		this.userTimestap = userTimestap;
	}
	public ApplyForUse() {
	}
	
	
}