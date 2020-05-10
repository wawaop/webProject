package com.guet.graduation.cfq.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface ManageUseApplyService {
	
	/**
	 * 获取所有的使用申请
	 * @param map
	 * @return
	 */
	public String getAllUseApplication(Map<String, Object> map);
	
	/**
	 * 通过使用申请
	 * @param params
	 * @return
	 */
	public String passUseApply(JSONObject params);
	
	/**
	 * 不予通过使用申请
	 * @param params
	 * @return
	 */
	public String unPassUseApply(JSONObject params);
}
