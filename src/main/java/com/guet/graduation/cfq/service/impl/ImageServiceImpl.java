package com.guet.graduation.cfq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.dao.EquipmentImageMapper;
import com.guet.graduation.cfq.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private EquipmentImageMapper equipmentImageMapper;

	@Override
	public String deleteImageById(JSONObject params) {
		String imageId=params.getString("imageId");
		equipmentImageMapper.deleteImageById(imageId);
		return "成功";
	}

}
