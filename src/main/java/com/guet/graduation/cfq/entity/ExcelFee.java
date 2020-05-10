package com.guet.graduation.cfq.entity;

/**
 * 该类用于保存Excel中收入sheet的数据库查询结果
 * @author 123
 *
 */
public class ExcelFee {

	String equipmentName;
	
	//总价
	Float price;

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public ExcelFee(String equipmentName, Float price) {
		super();
		this.equipmentName = equipmentName;
		this.price = price;
	}

	public ExcelFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
