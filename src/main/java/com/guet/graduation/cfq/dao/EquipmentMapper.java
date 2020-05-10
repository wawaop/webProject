package com.guet.graduation.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.PublishApply;

@Mapper
public interface EquipmentMapper {
	
	/**
	 * 向数据库中插入设备
	 * @param equipment
	 */
	public void insertEquipment(Equipment equipment);
	
	public List<Equipment> getEquipmentByUserId(String userId);
	
	public void deleteEuipmentById(String equipmentId);
	
	public void insertPublishApply(PublishApply publishApply);
	
	public Equipment getEquipmentById(String equipmentId);
	
	public void updateEquipment(Equipment equipment);
	
	public void updateEquipmentStatus(String equipmentId);
	
	/**
	 * 根据设备名查找该用户的设备
	 * @param equipmentName
	 * @param userId
	 * @return
	 */
	public List<Equipment> searchEquipment(Equipment equipment);

}
