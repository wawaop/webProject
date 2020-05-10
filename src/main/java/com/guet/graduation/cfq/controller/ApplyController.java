package com.guet.graduation.cfq.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.entity.PublishApply;
import com.guet.graduation.cfq.service.ApplyService;

@Controller
public class ApplyController {
	
	@Autowired
	ApplyService applyService;
	
	@RequestMapping("/publishApplication")
	public String publishApplication(Map<String, Object> map) {
		//获取所有需要进行审批的设备的
		applyService.getPublisApply(map);
		
		return "publishApplication.html";
	}
	
	/**
	 * 通过申请遍号，获取申请的具体详细信息
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(path="/turnToDetailApply/{applyId}")
	public String turnToDetailApply(@PathVariable("applyId") String applyId,Map<String,Object> map) {
		System.out.println("得到的查看详情的申请编号为："+applyId);
		applyService.getDetailApplyById(applyId, map);
		return "detailApply.html";
	}
	
	@ResponseBody
	@RequestMapping("/passApply")
	public String passApply(@RequestBody JSONObject params) {
		System.out.println("得到的通过发布申请的申请编号为："+params.getString("passID"));
		//修改该申请编号的状态为通过,然后通过编号得到设备ID，将该设备修改为可使用
		applyService.passApply(params);
		return "处理成功";
	}
	
	@ResponseBody
	@RequestMapping("turnToDetailApply/passApply")
	public String passDetailApply(@RequestBody JSONObject params) {
		System.out.println("得到的通过发布申请的申请编号为："+params.getString("passID"));
		//修改该申请编号的状态为通过,然后通过编号得到设备ID，将该设备修改为可使用
		applyService.passApply(params);
		return "处理成功";
	}
	
	@ResponseBody
	@RequestMapping("/unPassApply")
	public String unPassApply(@RequestBody JSONObject params) {
		System.out.println("得到的不予通过发布申请的申请编号为："+params.getString("unPassID"));
		//修改该申请编号的状态为不通过，设备依旧为不可见
		applyService.unPassApply(params);
		return "处理成功";
	}
	
	@ResponseBody
	@RequestMapping("turnToDetailApply/unPassApply")
	public String unPassDetailApply(@RequestBody JSONObject params) {
		System.out.println("得到的不予通过发布申请的申请编号为："+params.getString("unPassID"));
		//修改该申请编号的状态为不通过，设备依旧为不可见
		applyService.unPassApply(params);
		return "处理成功";
	}
	
	/**
	 * “我的设备发布申请”界面
	 * @return
	 */
	@RequestMapping("/myPublishApply")
	public String myPublishApply(HttpSession session, Map<String,Object> map,
								@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
								@RequestParam(defaultValue="8",value="pageSize")Integer pageSize,
								@RequestParam(required = false,defaultValue="1",value="applyPageNum")Integer applyPageNum,
								@RequestParam(defaultValue="8",value="applyPageSize")Integer applyPageSize,
								@RequestParam(required = false,defaultValue="1",value="passPageNum")Integer passPageNum,
								@RequestParam(defaultValue="8",value="passPageSize")Integer passPageSize,
								@RequestParam(required = false,defaultValue="1",value="unPassPageNum")Integer unPassPageNum,
								@RequestParam(defaultValue="8",value="unPassPageSize")Integer unPassPageSize,
								@RequestParam(defaultValue="1",value="applyTabNum")Integer applyTabNum) {
		applyService.myPublishApply(session, map, pageNum, pageSize, applyPageNum, applyPageSize, passPageNum, passPageSize, unPassPageNum, unPassPageSize, applyTabNum);
		return "myPublishApply.html";
	}
}
