package com.guet.graduation.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.DateAndUserId;
import com.guet.graduation.cfq.entity.EquipmentUseTimes;
import com.guet.graduation.cfq.entity.ExcelFee;

@Mapper
public interface DataAnalysisMapper {
	
	/**
	 * 获取设备使用次数
	 * @param dateAndUserId
	 * @return 设备名、设备在该天的使用次数、日期——即EquipmentUseTimes
	 */
	public List<EquipmentUseTimes> getEquipmentUseTimes(DateAndUserId dateAndUserId);
	
	/**
	 * 获取该用户时间段内的收入
	 * @param dateAndUserId
	 * @return
	 */
	public List<ExcelFee> getFee(DateAndUserId dateAndUserId);

}
