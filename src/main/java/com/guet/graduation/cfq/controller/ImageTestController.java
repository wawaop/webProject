package com.guet.graduation.cfq.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class ImageTestController {

	@ResponseBody 
	@RequestMapping(value = "/updatePersonalById")
	public JSONObject updatePersonal(@RequestParam("file") MultipartFile file, @ModelAttribute String ui,HttpServletRequest request) throws IllegalStateException, IOException {
		String oldName = file.getOriginalFilename();
		System.out.println("得到的文件名为："+oldName);
		String userName=request.getParameter("userName");
		System.out.println("得到的username为："+userName);
		JSONObject json=new JSONObject();
		json.put("code", "成功");
		return json;
	}

}
