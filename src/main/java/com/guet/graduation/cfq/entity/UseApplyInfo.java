package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * “设备使用申请”界面需要展示的信息
 * @author 123
 *
 */
public class UseApplyInfo {
	//申请编号
	String apply_id;
	//设备名
	String equipmentName;
	//设备种类
	String type;
	//申请人
	String login;
	//申请提出时间
	Timestamp apply_date;
	//申请的使用日期
	Date useTime;
	//申请的使用时刻
	String timeStamp;
	//单价
	double unitPrice;
	//总价
	double price;
	
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Timestamp getApply_date() {
		return apply_date;
	}
	public void setApply_date(Timestamp apply_date) {
		this.apply_date = apply_date;
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
	public UseApplyInfo(String apply_id, String equipmentName, String type, String login, Timestamp apply_date,
			Date useTime, String timeStamp, double unitPrice, double price) {
		super();
		this.apply_id = apply_id;
		this.equipmentName = equipmentName;
		this.type = type;
		this.login = login;
		this.apply_date = apply_date;
		this.useTime = useTime;
		this.timeStamp = timeStamp;
		this.unitPrice = unitPrice;
		this.price = price;
	}
	
	public UseApplyInfo() {
	}

}
