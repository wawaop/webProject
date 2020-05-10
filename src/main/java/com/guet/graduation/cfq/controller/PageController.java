package com.guet.graduation.cfq.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {

	
	@RequestMapping("/turnToLogin")
	public String turnToLogin() {
		return "login.html";
	}
	
	@RequestMapping("/turnToRegister")
	public String turnToRegister() {
		return "register.html";
	}
	
	@RequestMapping("/personalInformation")
	public String personalInformation() {
		return "register.html";
	}
	
	@RequestMapping("/head")
	public String head() {
		return "head.html";
	}
	
	@RequestMapping("/tableTest")
	public String tableTest(HttpServletRequest request) {
		String provice=request.getParameter("positionProvince");

		System.out.println("得到的省为："+provice);
		return "tableTest.html";
	}
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request) {
		String provice=request.getParameter("positionProvince");
		System.out.println("得到的省为："+provice);
	}
	
	@RequestMapping("/addEquipmentPage")
	public String addEquipmentPage() {
		return "addSpaceAndEquipment.html";
	}
	
	@RequestMapping("/turnToDataAnalysis")
	public String turnToDataAnalysis() {
		return "dataAnalysis.html";
	}
	
	@RequestMapping("/feedback")
	public String feedback() {
		return "feedback.html";
	}
	
	@RequestMapping("/turnToImage")
	public String turnToImage() {
		return "ImageTest.html";
	}
	
}
