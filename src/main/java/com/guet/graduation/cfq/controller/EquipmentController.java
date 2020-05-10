package com.guet.graduation.cfq.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.service.EquipmentService;
import com.guet.graduation.cfq.service.ImageService;
import com.guet.graduation.cfq.service.UserService;

@Controller
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;

	@ResponseBody
	@RequestMapping(path="/addEquipment")
	public String  addEquipment(@RequestBody JSONObject params,HttpSession session){
		System.out.println("addEqupment中得到的原来的文字数据为："+params.getString("inputDate")+"得到的图片数据为："+params.getString("imageUrl"));
		//将前端的数据弄进数据库处理
		equipmentService.addEquipment(params, session);
		JSONObject json=new JSONObject();
		json.put("code", "成功");
		return "成功";
	}
	
	/**
	 * 添加图片返回图片地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addImage")
	public JSONObject addImage(@RequestParam("file") MultipartFile file) {
		System.out.println("从前端得到的图片名为："+file.getOriginalFilename());
		String newImageName=userService.saveImage(file);
		System.out.println("返回给前端的路径为："+newImageName);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result", newImageName);
		return jsonObject;
	}
	
	@PutMapping("/profiles")
    public String setUserProfile(@RequestParam(required = true) MultipartFile profile) {
		System.out.println("得到的文件名为："+profile.getOriginalFilename());
        return "成功";
    }
	
	
	@RequestMapping("/manageEquipment")
	public String publishEquipment(Map<String, Object> map,HttpSession session) {
		//从数据库库中查找属于该用户的设备，然后保存在map中
		equipmentService.getEquipmentByUserId(map, session);
		return "manageEquipment.html";
	}

	
	/**
	 * 删除设备/空间
	 * @param map
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteEquipment")
	public String deleteEquipment(@RequestBody JSONObject params) {
		System.out.println("得到从前端传递过来的删除的设备编号为："+params.getString("deleteEquipmentId"));
		equipmentService.deleteEquipment(params);
		return "删除成功";
	}
	
	/**
	 * 编辑获取原数据
	 * @param map
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOldEquipmentPage")
	public String EditEquipmentPage(@RequestBody JSONObject params,Map<String, Object> map) {
		System.out.println("得到从前端传递过来的进行编辑的设备编号为："+params.getString("JsonEditId"));
		//从数据库库中查找属于该用户的设备，然后保存在map中
		return "成功";
	}
	
	/**
	 * 获取编辑的表格信息，填充界面
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/turnToEditEquipmentPage/{id}")
	public String EditEquipmentPage(@PathVariable("id") String id,Map<String,Object> map) {
		System.out.println("得到的想要编辑的设备ID为："+id);
		equipmentService.getOldEquipment(id, map);
		return "editEquipment.html";
	}
	
	@ResponseBody
	@RequestMapping(path="/updateEquipment")
	public String updateEquipment(@RequestBody JSONObject params,HttpSession session) {
		System.out.println("想要更新的的设备ID为："+params.getString("equipmentId"));
		equipmentService.updateEquipment(params);
		return "更新成功";
	}
	
	/**
	 * 根据名称查找该用户的设备
	 * @return
	 */
	@RequestMapping("/searchEquipment")
	public String searchEquipment(HttpServletRequest request,Map<String,Object> map,HttpSession session) {
		//查找用户设备放置在map中
		equipmentService.searchEquipment(request, map, session);
		return "manageEquipment.html";
	}
	
}
