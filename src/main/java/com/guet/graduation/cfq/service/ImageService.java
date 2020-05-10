package com.guet.graduation.cfq.service;

import com.alibaba.fastjson.JSONObject;

public interface ImageService {

	/**
	 * 根据图片ID删除图片
	 * @param params
	 * @return
	 */
	public String deleteImageById(JSONObject params);
}
