package com.guet.graduation.cfq.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.ApplyForUse;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.StarTimeAndEndTime;

@Mapper
public interface ApplyForUseMapper {
	
	/**
	 * 通过省、市、类型来获取所有符合条件的设备
	 * @param equipment
	 * @return
	 */
	public List<Equipment> getEquipmentByPosition(Equipment equipment);
	
	
	/**
	 * 将申请信息插入申请表中
	 * @param applyForUse
	 */
	public void insertApplyForUse(ApplyForUse applyForUse);
	
	/**
	 * 获取所待处理的使用申请信息
	 * @return
	 */
	public List<ApplyForUse> getAllUseApplication();
	
	/**
	 * 获取该用户所拥有设备的所有待处理的申请信息
	 * @param userId
	 * @return
	 */
	public List<ApplyForUse> getAllUseApplicationByUserId(String userId);
	
	/**
	 * 通过申请ID修改指定申请的申请状态为通过
	 * @param apply_id
	 */
	public void updateApplyStatusToPass(String apply_id);
	
	/**
	 * 通过申请ID修改指定申请的申请状态为不通过
	 * @param apply_id
	 */
	public void updateApplyStatusToUnPass(String apply_id);
	
	/**
	 * 通过使用申请的申请ID获取该条申请记录
	 * @param apply_id
	 * @return
	 */
	public ApplyForUse getApplyForUseById(String apply_id);
	
	/**
	 * 通过使用时间和设备ID获取该设备在该时间内所提供状态的所有一一对应的开始时刻和结束时刻
	 * @param applyForUse
	 * @return
	 */
	public List<ApplyForUse> getAllStartTimeAndEndTime(ApplyForUse applyForUse);
	
	/**
	 * 获取"我的使用申请"——所有的申请数据
	 * @param userId
	 * @return
	 */
	public List<ApplyForUse> getMyApplyForUse(String userId);
	
	/**
	 * 获取用户“申请中”的申请数据
	 * @param userId
	 * @return
	 */
	public List<ApplyForUse> getApplyingUseByUseId(String userId);
	
	/**
	 * 获取用户“已通过”的申请数据
	 * @param userId
	 * @return
	 */
	public List<ApplyForUse> getPassApplyByUseId(String userId);
	
	/**
	 * 获取用户“不通过”的申请数据
	 * @param userId
	 * @return
	 */
	public List<ApplyForUse> getUnPassApplyByUseId(String userId);
	
	/**
	 * 获取在指定时间内的冲突申请
	 * @param starTimeAndEndTime
	 * @return
	 */
	public List<ApplyForUse> getConflicApply(ApplyForUse applyForUse);
	
	

}
