package com.guet.graduation.cfq.service;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.guet.graduation.cfq.entity.RoleFunction;

public interface UserService {
	
	public String login(String login,String password,Map<String, Object> map);
	
	public String regist(String login,String password);
	
	public Collection<RoleFunction> getFunction(HttpSession session);

	/**
	 * 保存图片，返回图片路径
	 * @param profile
	 * @return
	 */
	public String saveImage(MultipartFile profile);
	
	/**
	 * 获取用户个人信息，并存放在map中
	 * @param map
	 */
	public void getPersonalInformation(HttpSession session, Map<String, Object> map);
	
	/**
	 * 更新个人信息
	 * @param request
	 */
	public void updatePersonalInfo(HttpSession session, HttpServletRequest request);
	
}
