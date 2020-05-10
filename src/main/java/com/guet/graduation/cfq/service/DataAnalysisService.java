package com.guet.graduation.cfq.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

public interface DataAnalysisService {
	
	/**
	 * 获取“数据分析”中指定日期的数据
	 * @param request
	 * @return
	 */
	public JSONObject getAnalysisData(JSONObject params,HttpSession session);
	
	/**
	 * 输出Excel表格
	 * @param params
	 * @param session
	 * @param response
	 */
	public void exportData(String startDate,String endDate,HttpSession session,HttpServletResponse response);
//	public void exportData(HttpServletResponse response);

}
