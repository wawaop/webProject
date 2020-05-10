package com.guet.graduation.cfq.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;

import com.guet.graduation.cfq.entity.PublishApply;
import com.guet.graduation.cfq.entity.RoleFunction;
import com.guet.graduation.cfq.entity.User;

@Mapper
public interface UserMapper {
	
	/**
	 * 登录
	 */
	public String login(User user);
	
	/**
	 * 注册用户（向tb_user表中插入数据）
	 * @param user 用户对象
	 */
	public void inserUser(User user);
	
	/**
	 * 根据用户ID来获取其角色 
	 * @param userId
	 * @return 角色ID
	 */
	public int getRoleId(String userId);
	
	/**
	 * 根据角色ID获取其菜单
	 * @param roleId
	 * @return
	 */
	public Collection<RoleFunction> getFunctions(int roleId);
	
	/**
	 * 根据用户ID获取用户名
	 * @param userId
	 * @return
	 */
	public String getUserNameById(String userId);
	
	/**
	 * 根据用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	public User getUser(String userId);
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 更新密码
	 * @param user
	 */
	public void updatePassword(User user);
}
