package com.guet.graduation.cfq.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.PublishApply;

@Mapper
public interface ApplyMapper {

	/**
	 * 获取发布申请表中的所有申请
	 * @return
	 */
	public List<PublishApply> getPublishApply();
	
	/**
	 * 根据ID获取对应的申请的所有详细信息
	 * @param applyId
	 * @return
	 */
	public PublishApply getPublishApplyById(String applyId);
	
	/**
	 * 将该申请编号的信息状态修改为已通过
	 * @param applyId
	 */
	public void passApply(String applyId);
	
	/**
	 * 通过申请ID获取设备ID
	 * @param applyId
	 * @return
	 */
	public String getEquipmentIdById(String applyId);
	
	/**
	 * 将申请状态修改为不通过
	 * @param unPassId
	 * @return
	 */
	public void unPassApply(String unPassId);
	
	/**
	 * 根据用户ID获取属于该用户的所有发布申请
	 * @param userId
	 * @return
	 */
	public List<PublishApply> getMyPublishApply(String userId);
	
	/**
	 * 获取该用户“申请中”的发布申请
	 * @param userId
	 * @return
	 */
	public List<PublishApply> getApplying(String userId);
	
	/**
	 * 获取用户“已通过”的发布申请
	 * @param userId
	 * @return
	 */
	public List<PublishApply> getPassApply(String userId);
	
	/**
	 * 获取用户“不通过”的发布申请
	 * @param userId
	 * @return
	 */
	public List<PublishApply> getUnPassApply(String userId);
	
	/**
	 * 删除该设备的发布申请
	 * @param equipmentId
	 */
	public void deleteApply(String equipmentId);
}
