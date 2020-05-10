package com.guet.graduation.cfq.entity;

import java.sql.Time;

/**
 * @author 123
 *
 */
public class Equipment {
	
	//设备编号
	String equipmentId;
	
	//设备拥有者编号
	String userId;
	
	//设备名
	String equipmentName;
	
	//设备描述信息
	String equipmentDescrip;
	
	//设备容纳人数
	int equipmentCapacity;
	
	//设备状态
	String equipmentsStatus;
	
	//设备地址——省
	String positionProvince;
	
	//设备地址——市
	String positionCity;
	
	//设备地址——区
	String positionSpecificLocation;
	
	//设备开放时间——开时间
	Time openingTime;
	
	//设备开放时间——闭时间
	Time closeTime;
	
	//设备类型
	String type;
	
	//设备单价
	double price;
	
	//是否需要申请
	boolean isNeedApply;
	
	public Equipment(String equipmentId, String equipmentName, String equipmentDescrip, int equipmentCapacity,
			String equipmentsStatus, Time openingTime, Time closeTime, String type, double price) {
		super();
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.equipmentDescrip = equipmentDescrip;
		this.equipmentCapacity = equipmentCapacity;
		this.equipmentsStatus = equipmentsStatus;
		this.openingTime = openingTime;
		this.closeTime = closeTime;
		this.type = type;
		this.price = price;
	}

	public Equipment(String equipmentId, String userId, String equipmentName, String equipmentDescrip,
			int equipmentCapacity, String status, String positionProvince, String positionCity,
			String positionSpecificLocation, Time openingTime, Time closeTime, String type, double price,
			boolean isNeedApply) {
		this.equipmentId = equipmentId;
		this.userId = userId;
		this.equipmentName = equipmentName;
		this.equipmentDescrip = equipmentDescrip;
		this.equipmentCapacity = equipmentCapacity;
		this.equipmentsStatus = status;
		this.positionProvince = positionProvince;
		this.positionCity = positionCity;
		this.positionSpecificLocation = positionSpecificLocation;
		this.openingTime = openingTime;
		this.closeTime = closeTime;
		this.type = type;
		this.price = price;
		this.isNeedApply = isNeedApply;
	}

	public Equipment() {
		
	}

	public  Equipment(String positionProvince,String positionCity,String type) {
		this.positionProvince=positionProvince;
		this.positionCity=positionCity;
		this.type=type;
	}
	
	public String getEquipmentId() {
		return equipmentId;
	}



	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getEquipmentName() {
		return equipmentName;
	}



	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}



	public String getEquipmentDescrip() {
		return equipmentDescrip;
	}



	public void setEquipmentDescrip(String equipmentDescrip) {
		this.equipmentDescrip = equipmentDescrip;
	}



	public int getEquipmentCapacity() {
		return equipmentCapacity;
	}



	public void setEquipmentCapacity(int equipmentCapacity) {
		this.equipmentCapacity = equipmentCapacity;
	}



	public String getEquipmentsStatus() {
		return equipmentsStatus;
	}



	public void setEquipmentsStatus(String equipmentsStatus) {
		this.equipmentsStatus = equipmentsStatus;
	}



	public String getPositionProvince() {
		return positionProvince;
	}



	public void setPositionProvince(String positionProvince) {
		this.positionProvince = positionProvince;
	}



	public String getPositionCity() {
		return positionCity;
	}



	public void setPositionCity(String positionCity) {
		this.positionCity = positionCity;
	}



	public String getPositionSpecificLocation() {
		return positionSpecificLocation;
	}



	public void setPositionSpecificLocation(String positionSpecificLocation) {
		this.positionSpecificLocation = positionSpecificLocation;
	}



	public Time getOpeningTime() {
		return openingTime;
	}



	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}



	public Time getCloseTime() {
		return closeTime;
	}



	public void setCloseTime(Time closeTime) {
		this.closeTime = closeTime;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public boolean isNeedApply() {
		return isNeedApply;
	}



	public void setNeedApply(boolean isNeedApply) {
		this.isNeedApply = isNeedApply;
	}

	public Equipment(String userId, String equipmentName) {
		super();
		this.userId = userId;
		this.equipmentName = equipmentName;
	}
	
	

}
