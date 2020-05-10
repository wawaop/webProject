package com.guet.graduation.cfq.entity;

/**
 * 这个类是用于存储"我的使用申请"界面的信息的
 * @author 123
 *
 */
public class MyUseApply {
	
	ApplyForUse applyForUse;
	
	Equipment equipment;

	public ApplyForUse getApplyForUse() {
		return applyForUse;
	}

	public void setApplyForUse(ApplyForUse applyForUse) {
		this.applyForUse = applyForUse;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public MyUseApply(ApplyForUse applyForUse, Equipment equipment) {
		super();
		this.applyForUse = applyForUse;
		this.equipment = equipment;
	}

	public MyUseApply() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
