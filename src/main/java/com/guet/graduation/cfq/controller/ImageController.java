package com.guet.graduation.cfq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.service.ImageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@ResponseBody
	@RequestMapping("/deleteEquipmentImage")
	public String deleteImageById(@RequestBody JSONObject params) {
		String result=imageService.deleteImageById(params);
		return "成功";
	}

}
