package com.guet.graduation.cfq.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.guet.graduation.cfq.dao.UserMapper;
import com.guet.graduation.cfq.entity.RoleFunction;
import com.guet.graduation.cfq.entity.User;
import com.guet.graduation.cfq.service.UserService;
import com.guet.graduation.cfq.util.SymmetricEncoder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	// 测试图片上传路径
	public static final String LINUX_PROFILES_PATH = "/root/projectImage/";
	public static final String WINDOWS_PROFILES_PATH = "E:/projectImage/";

	/**
	 * 登录
	 * 
	 * @param login
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param map
	 *            错误时的返回信息
	 */
	@Override
	public String login(String login, String password, Map<String, Object> map) {
		// 将用户名加密，和数据库中的加密数据匹配
		login = SymmetricEncoder.AESEncode(login);
		password = SymmetricEncoder.AESEncode(password);
		String userId = userMapper.login(new User(login, password));
		return userId;
	}

	/**
	 * 注册
	 * 
	 * @param login
	 *            用户登录名
	 * @param password
	 *            用户密码
	 */
	@Override
	public String regist(String login, String password) {
		// 设置用户名和密码之外的信息
		// 生成UUID
		String userId = UUID.randomUUID().toString().replaceAll("-", "");
		// 注册的用户只能是普通用户
		int roleId = 3;
		// 加密用户名密码
		login = SymmetricEncoder.AESEncode(login);
		password = SymmetricEncoder.AESEncode(password);
		userMapper.inserUser(new User(userId, roleId, login, password));
		return "login.html";
	}

	/**
	 * 获取用户功能菜单 从session中获取用户ID，得到用户角色ID，通过角色ID在tb_function表中查找对应角色的功能菜单
	 */
	@Override
	public Collection<RoleFunction> getFunction(HttpSession session) {
		// 从session中获取用户ID
		String userId = (String) session.getAttribute("userId");
		System.out.println("得到的用户ID为：" + userId);
		// 根据用户ID获取用户角色信息
		int roleId = userMapper.getRoleId(userId);
		// 根据得到的roleID获取用户对应的角色菜单
		Collection<RoleFunction> functions = userMapper.getFunctions(roleId);
		return functions;
	}

	/**
	 * 将图片保存，并返回路径
	 */
	@Override
	public String saveImage(MultipartFile newProfile) {
		// 根据Windows和Linux配置不同的头像保存路径
		String OSName = System.getProperty("os.name");
		String profilesPath = OSName.toLowerCase().startsWith("win") ? WINDOWS_PROFILES_PATH : LINUX_PROFILES_PATH;

		if (!newProfile.isEmpty()) {
			// 当前用户
			// String profilePathAndNameDB =
			// userDao.selectUserById(currentUser.getUserId()).getProfilePath();
			String newProfileName = profilesPath + System.currentTimeMillis() + newProfile.getOriginalFilename();
			

			// 磁盘保存
			BufferedOutputStream out = null;
			try {
				File folder = new File(profilesPath);
				if (!folder.exists())
					folder.mkdirs();
				out = new BufferedOutputStream(new FileOutputStream(newProfileName));
				// 写入新文件
				out.write(newProfile.getBytes());
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return newProfileName;
		} else {
			return "fail";
		}
	}

	@Override
	public void getPersonalInformation(HttpSession session, Map<String, Object> map) {
		// 获取用户基本信息
		String userId = (String) session.getAttribute("userId");
		User user = userMapper.getUser(userId);
		// 解密登录名
		String login = user.getLogin();
		login = SymmetricEncoder.AESDncode(login);
		user.setLogin(login);
		// 将用户信息放在map中
		map.put("userInfo", user);
	}

	@Override
	public void updatePersonalInfo(HttpSession session, HttpServletRequest request) {
		String userId = (String) session.getAttribute("userId");
		String sex = request.getParameter("sex");
		if(sex==null) {
			return;
		}
		String birth = request.getParameter("brith");
		// 申请的使用日期
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = formatDate.parse(birth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date birthday = new java.sql.Date(d.getTime());
		
		String password = request.getParameter("password");
		// 更新数据库
		User user = new User(userId, sex, birthday);
		userMapper.updateUser(user);
		if (password != "") {
			//如果密码有更新，则更新数据库密码
			password = SymmetricEncoder.AESEncode(password);
			User user2=new User(userId, password, sex, birthday);
			userMapper.updatePassword(user2);
		}

	}

}
