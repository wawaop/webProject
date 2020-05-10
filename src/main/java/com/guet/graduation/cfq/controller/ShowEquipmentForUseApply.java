package com.guet.graduation.cfq.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.service.ApplyForUseService;

@Controller
public class ShowEquipmentForUseApply {

	@Autowired
	ApplyForUseService applyForUse;

	/**
	 * 转向申请界面，并显示内容
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/turnToApplyKaZuo")
	public String turnToApplyKaZuo(HttpServletRequest request,Map<String, Object> map) {
//		String provice=request.getParameter("positionProvince");
//		System.out.println("得到的省为："+provice);
		applyForUse.searchKaZuo(request, map);
		return "showEquipment.html";
	}
	
	/**
	 * 设备使用申请过滤
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/filterApply")
	public String filterApply(HttpSession session) {
		//当未登陆的时候跳转到登录界面
		System.out.println("执行到了过滤请求");
		String userId=(String) session.getAttribute("userId");
		if(userId == null ) {
			return "0";
		}else {
			return "1";
		}
		
	}
	
	
	/**
	 * 申请设备/空间
	 * @param params
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/applyKazuo")
	public String applyKazuo(@RequestBody JSONObject params,HttpSession session) {
		System.out.println("/applyKazuo得到的信息为:"+params.toString());
		return applyForUse.applyKaZuo(params, session);
//		return "成功";
	}
	
	
}
