package com.guet.graduation.cfq.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 该类用于保存Excel表中需要的设备使用次数的数据库返回值
 * @author 123
 *
 */
public class EquipmentUseTimes {
	
	String equipmentName;
	
	Integer useTimes;
	
	String useDate;

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public int getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public EquipmentUseTimes(String equipmentName, int useTimes, String useDate) {
		super();
		this.equipmentName = equipmentName;
		this.useTimes = useTimes;
		this.useDate = useDate;
	}

	public EquipmentUseTimes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
