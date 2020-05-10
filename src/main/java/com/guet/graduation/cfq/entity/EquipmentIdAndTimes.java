package com.guet.graduation.cfq.entity;


/**
 * 这个类是用于查找总价时候的参数——equipmentId、starTime、endTime
 * @author 123
 *
 */
public class EquipmentIdAndTimes {
	
	String equipmentId;
	
	String starTime;
	
	String endTime;

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getStarTime() {
		return starTime;
	}

	public void setStarTime(String starTime) {
		this.starTime = starTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public EquipmentIdAndTimes(String equipmentId, String starTime, String endTime) {
		super();
		this.equipmentId = equipmentId;
		this.starTime = starTime;
		this.endTime = endTime;
	}

	public EquipmentIdAndTimes() {
	}
}
