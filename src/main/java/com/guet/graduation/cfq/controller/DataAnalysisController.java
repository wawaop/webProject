package com.guet.graduation.cfq.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.guet.graduation.cfq.service.DataAnalysisService;

@Controller
public class DataAnalysisController {

	@Autowired
	private DataAnalysisService dataAnalysisService;

	@ResponseBody
	@RequestMapping("/analysisData")
	public String getAnalysisData(@RequestBody JSONObject params, HttpSession session) {
		System.out.println("得到的开始结束日期为：" + params.getString("startDate") + " " + params.getString("endDate"));
		String data = dataAnalysisService.getAnalysisData(params, session).toString();
		return data;
	}
	// @RequestBody JSONObject params,HttpSession session,HttpServletResponse
	// response

	
	@RequestMapping("/exportData")
	public void exportData(@PathParam("startDate")String startDate,@PathParam("endDate")String endDate, HttpServletResponse response,HttpSession session) {
		 System.out.println("得到的开始结束日期为："+startDate+" "+endDate);

		// 输出Excel表格
		dataAnalysisService.exportData(startDate, endDate, session, response);
	}
}
