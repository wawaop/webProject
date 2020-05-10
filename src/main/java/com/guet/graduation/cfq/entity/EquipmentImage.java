package com.guet.graduation.cfq.entity;

public class EquipmentImage {

	Integer image_id;
	
	String equipment_id;
	
	String image_url;

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public String getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(String equipment_id) {
		this.equipment_id = equipment_id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public EquipmentImage(String equipment_id, String image_url) {
		super();
		this.equipment_id = equipment_id;
		this.image_url = image_url;
	}

	public EquipmentImage(Integer image_id, String equipment_id, String image_url) {
		super();
		this.image_id = image_id;
		this.equipment_id = equipment_id;
		this.image_url = image_url;
	}
	
	
}
