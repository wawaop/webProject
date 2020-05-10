package com.guet.graduation.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.EquipmentImage;

@Mapper
public interface EquipmentImageMapper {
	
	/**
	 * 将图片信息插入到equipmentImage表中
	 * @param equipmentImage
	 */
	public void insertEquipmentImage(EquipmentImage equipmentImage);
	
	/**
	 * 通过equipmentID获取图片URL（只取一张用于界面显示）
	 * @param equipmentId
	 * @return
	 */
	public String getImageUrlById(String equipmentId);
	
	/**
	 * 获取设备ID的所有图片
	 * @param equipmentId
	 * @return
	 */
	public List<EquipmentImage> gEquipmentImages(String equipmentId);
	
	/**
	 * 根据ID删除图片
	 * @param imageId
	 */
	public void deleteImageById(String imageId);

}
