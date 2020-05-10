package com.guet.graduation.cfq.entity;

import java.awt.Image;
import java.util.List;

public class PublishApplyInfo {

	String userName;
	
	PublishApply publishApply;
	
	Equipment equipment;
	
	List<EquipmentImage> equipmentImages;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PublishApply getPublishApply() {
		return publishApply;
	}

	public void setPublishApply(PublishApply publishApply) {
		this.publishApply = publishApply;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public PublishApplyInfo() {
	}
	
	public List<EquipmentImage> getEquipmentImages() {
		return equipmentImages;
	}

	public void setEquipmentImages(List<EquipmentImage> equipmentImages) {
		this.equipmentImages = equipmentImages;
	}
	
	public PublishApplyInfo(String userName, PublishApply publishApply, Equipment equipment) {
		super();
		this.userName = userName;
		this.publishApply = publishApply;
		this.equipment = equipment;
	}

	public PublishApplyInfo(String userName, PublishApply publishApply, Equipment equipment,List<EquipmentImage> equipmentImages) {
		super();
		this.userName = userName;
		this.publishApply = publishApply;
		this.equipment = equipment;
		this.equipmentImages=equipmentImages;
	}
	
	
}
