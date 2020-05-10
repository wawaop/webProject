package com.guet.graduation.cfq.controller;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.entity.ApplyForUse;
import com.guet.graduation.cfq.service.AllocationAlgorithmService;
import com.guet.graduation.cfq.service.ApplyForUseService;
import com.guet.graduation.cfq.service.ManageUseApplyService;

/**
 * 管理使用申请
 * @author 123
 *
 */
@Controller
public class UseApplyController {
	
	@Autowired
	ManageUseApplyService manageUseApplyService;
	
	@Autowired
	AllocationAlgorithmService allocationAlgorithmService;
	
	@Autowired
	private ApplyForUseService applyForUse;
	
	
	/**
	 * 转向“设备使用申请处理”界面，并将所有的使用申请返回到界面
	 * @param map
	 * @return
	 */
	@RequestMapping("/turnToUseApply")
	public String manageUseApplication(Map<String, Object> map,HttpSession session) {
		//没有算法的
//		manageUseApplyService.getAllUseApplication(map);
//		return "useApply.html";
		
		//获取所有待审批的使用申请（算法分配）
		allocationAlgorithmService.Allocation(session, map);
		return "useApplyWithAlgorithm.html";
	}
	
	@ResponseBody
	@RequestMapping("/passUseApply")
	public String passUseApply(@RequestBody JSONObject params) {
		System.out.println("得到的通过使用申请的申请编号为："+params.getString("passID"));
		return manageUseApplyService.passUseApply(params);
//		return "操作成功";
	}
	
	@ResponseBody
	@RequestMapping("/unPassUseApply")
	public String unPassUseApply(@RequestBody JSONObject params) {
		System.out.println("得到的不予通过的使用申请的申请编号为："+params.getString("passID"));
		manageUseApplyService.unPassUseApply(params);
		return "操作成功";
	}
	
	/**
	 * “我的使用申请”界面的内容
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/myUseApply")
	public String myUseApply(HttpSession session, Map<String, Object> map,
							@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
							@RequestParam(defaultValue="8",value="pageSize")Integer pageSize,
							@RequestParam(required = false,defaultValue="1",value="applyPageNum")Integer applyPageNum,
							@RequestParam(defaultValue="8",value="applyPageSize")Integer applyPageSize,
							@RequestParam(required = false,defaultValue="1",value="passPageNum")Integer passPageNum,
							@RequestParam(defaultValue="8",value="passPageSize")Integer passPageSize,
							@RequestParam(required = false,defaultValue="1",value="unPassPageNum")Integer unPassPageNum,
							@RequestParam(defaultValue="8",value="unPassPageSize")Integer unPassPageSize,
							@RequestParam(defaultValue="1",value="userApplytabNum")Integer userApplytabNum) {
//		applyForUse.myUseApply(session, map);
		applyForUse.myUseApply(session, map, pageNum, pageSize, applyPageNum, applyPageSize, passPageNum, passPageSize, unPassPageNum, unPassPageSize, userApplytabNum);
		return "myUseApply.html";
	}

}
