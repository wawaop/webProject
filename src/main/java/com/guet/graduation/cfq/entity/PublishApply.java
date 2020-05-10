package com.guet.graduation.cfq.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class PublishApply {
	
	//申请编号
	String publishId;
	
	//申请的用户ID
	String userId;
	
	//申请发布的设备编号
	String equipmentId;
	
	//申请的时间
	Timestamp applyTime;
	
	//申请的状态
	String applystatus;
	
	//设备名
	String equipmentName;
	
	//设备类型
	String type;
	
	//设备单价
	Float price;

	public PublishApply() {
	}

	public PublishApply(String publishId, String userId, String equipmentId, Timestamp applyTime, String applystatus) {
		super();
		this.publishId = publishId;
		this.userId = userId;
		this.equipmentId = equipmentId;
		this.applyTime = applyTime;
		this.applystatus = applystatus;
	}

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplystatus() {
		return applystatus;
	}

	public void setStatus(String status) {
		this.applystatus = status;
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

	public void setApplystatus(String applystatus) {
		this.applystatus = applystatus;
	}

	public PublishApply(String publishId, Timestamp applyTime, String equipmentName, String type, Float price) {
		super();
		this.publishId = publishId;
		this.applyTime = applyTime;
		this.equipmentName = equipmentName;
		this.type = type;
		this.price = price;
	}
	
}
