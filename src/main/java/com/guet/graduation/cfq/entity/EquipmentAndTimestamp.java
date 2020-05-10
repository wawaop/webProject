package com.guet.graduation.cfq.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是在查找设备界面中显示的对象——设备的本身信息+设备被预约的信息
 * @author 123
 *
 */
public class EquipmentAndTimestamp {

	//设备信息
	private Equipment equipment;
	
	//该设备的“申请中”的时间
	private String applyingTime;
	
	//该设备的“已通过"的时间
	private String passTime;
	
	//设备的图片路径（取一个）
	private String imageUrl;

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public String getApplyingTime() {
		return applyingTime;
	}

	public void setApplyingTime(String applyingTime) {
		this.applyingTime = applyingTime;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public EquipmentAndTimestamp(Equipment equipment, String applyingTime, String passTime, String imageUrl) {
		super();
		this.equipment = equipment;
		this.applyingTime = applyingTime;
		this.passTime = passTime;
		this.imageUrl=imageUrl;
	}

	public EquipmentAndTimestamp() {
	}
	
}
