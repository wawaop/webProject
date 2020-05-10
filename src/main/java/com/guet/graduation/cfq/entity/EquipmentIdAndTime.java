package com.guet.graduation.cfq.entity;

/**
 * 这个类是为了根据设备和日期来查找总价的查找参数
 * @author 123
 *
 */
public class EquipmentIdAndTime {
	
	//设备ID
	String equipmentId;
	//时间
	String useTime;
	
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	
	public EquipmentIdAndTime(String equipmentId, String useTime) {
		super();
		this.equipmentId = equipmentId;
		this.useTime = useTime;
	}
	
	public EquipmentIdAndTime() {
	}
	
}
