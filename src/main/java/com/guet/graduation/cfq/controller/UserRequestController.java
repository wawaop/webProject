package com.guet.graduation.cfq.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Collection;
import java.util.Map;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guet.graduation.cfq.dao.UserMapper;
import com.guet.graduation.cfq.entity.RoleFunction;
import com.guet.graduation.cfq.entity.User;
import com.guet.graduation.cfq.service.UserService;

@Controller
public class UserRequestController {

	@Autowired
	private UserService userService;

	/**
	 * 登录
	 * 
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestParam("login") String login, @RequestParam("password") String password,
							Map<String, Object> map,HttpSession session) {
		// 如果登录成功，转到个人界面，否则还在登录界面
		String userId = userService.login(login, password, map);
		//如果userID==null则登录失败，否则登录成功
		if(userId==null) {
			map.put("errMsg", "用户名或密码错误");
			return "login.html";
		}
		else {
			//登录成功，将userID放置在session中
			session.setAttribute("userId", userId);
			//将用户名放置在session中
			session.setAttribute("userName", login);
//			map.put("userName", login);
			return "redirect:/turnToPersonalPage";
		}
	}

	/**
	 * 
	 * 注册
	 * 
	 */
	@RequestMapping(path = "/regist")
	public String register(@RequestParam("login") String login, 
						   @RequestParam("password") String password) {
		return userService.regist(login, password);
	}
	
	/**
	 * 转向登录界面，并处理菜单问题
	 * @param session
	 * @return
	 */
	@RequestMapping("/turnToPersonalPage")
	public String turnToPersonalPage(HttpSession session,Model model) {
		Collection<RoleFunction> functions=userService.getFunction(session);
		model.addAttribute("functions", functions);
		return "personal.html";
	}
	
	/*@PutMapping("/profiles")
    public String setUserProfile(@RequestParam(required = true) MultipartFile profile) {
        return userService.updUserProfile(profile);
    }*/
	
	/**
	 * 转向“个人信息”界面，并在其中显示个人信息
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String personalInformation(HttpSession session,HttpServletRequest request, Map<String, Object> map) {
		userService.updatePersonalInfo(session, request);
		userService.getPersonalInformation(session, map);
		return "personalInformation.html";
	}
	

}
