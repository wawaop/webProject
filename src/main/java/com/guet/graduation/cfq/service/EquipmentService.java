package com.guet.graduation.cfq.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.entity.Result;

public interface EquipmentService {
	
	public String addEquipment(JSONObject params,HttpSession session);
	
	public void getEquipmentByUserId(Map<String, Object> map,HttpSession session);
	
	public void deleteEquipment(JSONObject params);
	
	public void getOldEquipment(String equipmentId,Map<String,Object> map);
	
	public void updateEquipment(JSONObject params);
	
	/**
	 * 根据设备名，查找用户设备
	 * @param request
	 * @param map
	 * @param session
	 */
	public void searchEquipment(HttpServletRequest request,Map<String, Object> map ,HttpSession session);

}
