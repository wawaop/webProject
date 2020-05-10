package com.guet.graduation.cfq.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 用算法对使用申请进行分配建议显示处理
 * @author 123
 *
 */
public interface AllocationAlgorithmService {

	/**
	 * 分配处理(算法推荐）
	 * @param session
	 * @param map
	 */
	public void Allocation(HttpSession session,Map<String, Object> map); 
}
