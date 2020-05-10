package com.guet.graduation.cfq.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.assertj.core.api.IntArrayAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.dao.ApplyMapper;
import com.guet.graduation.cfq.dao.EquipmentImageMapper;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.EquipmentImage;
import com.guet.graduation.cfq.entity.PublishApply;
import com.guet.graduation.cfq.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentMapper equipmentMapper;

	@Autowired
	private EquipmentImageMapper equipmentImageMapper;
	
	@Autowired
	private ApplyMapper applyMapper;

	/**
	 * 将申请发布的设备/空间内容插入到数据库
	 */
	@Override
	public String addEquipment(JSONObject params, HttpSession session) {
		// 获取input数据，赋值给Equipment实体类
		JSONObject inputJsonData = JSONObject.parseObject(params.getString("inputDate"));
		Equipment equipment = setEquipment(inputJsonData, session);
		// 将Equipment插入到空间/设备数据库（设备状态为不可见）
		equipmentMapper.insertEquipment(equipment);
		// 获取申请数据
		PublishApply publishApply = setPublishApply(params, session, equipment);
		// 将equipment插入到设备/空间发布申请表中
		equipmentMapper.insertPublishApply(publishApply);

		// 向数据库保存设备图片信息——先获取image对象，然后将其insert如image表中
		// 路径字符串
		String imageUrls = params.getString("imageUrl");
		// 构造image对象
		List<EquipmentImage> equipmentImages = setEquipmentImage(equipment, imageUrls);
		// 依次将对象插入数据库
		for (EquipmentImage equipmentImage : equipmentImages) {
			equipmentImageMapper.insertEquipmentImage(equipmentImage);
		}

		return "成功";
	}

	/**
	 * 构造EquipmentImage对象
	 * 
	 * @param equipment
	 * @param imageUrls
	 * @return
	 */
	private List<EquipmentImage> setEquipmentImage(Equipment equipment, String imageUrls) {
		// String image_id=UUID.randomUUID().toString().replaceAll("-", "");
		String equipment_id = equipment.getEquipmentId();
		String image_url[] = imageUrls.split(",");
		List<EquipmentImage> equipmentImages = new ArrayList<EquipmentImage>();
		for (int i = 0; i < image_url.length; i++) {
			if ("".equals(image_url[i]) || image_url[i] == null) {
				continue;
			}
			EquipmentImage equipmentImage = new EquipmentImage(equipment_id, image_url[i].substring(16));
			equipmentImages.add(equipmentImage);
		}
		return equipmentImages;
	}

	private PublishApply setPublishApply(JSONObject params, HttpSession session, Equipment equipment) {
		// 申请编号
		String publishId = UUID.randomUUID().toString().replaceAll("-", "");
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		// 设备ID
		String equipmentId = equipment.getEquipmentId();
		// 申请时间
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp applyTime = new Timestamp(currentTime.getTime());
		// 申请状态
		String status = "申请中";
		// 构造申请类
		PublishApply publishApply = new PublishApply(publishId, userId, equipmentId, applyTime, status);
		return publishApply;
	}

	/**
	 * 将界面输入的值转为Equipment对象
	 * 
	 * @param params
	 * @param session
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Equipment setEquipment(JSONObject params, HttpSession session) {
		// 设备编号
		String equipmentId = UUID.randomUUID().toString().replaceAll("-", "");
		// 设备拥有者ID
		String userId = (String) session.getAttribute("userId");
		// 设备名
		String equipmentName = params.getString("equipmentName");
		// 设备描述信息
		String equipmentDescrip = params.getString("description");
		// 设备容纳人数
		int equipmentCapacity = params.getInteger("equipmentCapacity");
		// 设备状态
		String status = "申请中";
		// 设备地址
		// 设备地址——省
		String positionProvince = params.getString("positionProvince");

		// 设备地址——市
		String positionCity = params.getString("positionCity");

		// 设备地址——区
		String positionSpecificLocation = params.getString("positionSpecificLocation");

		String openTime = params.getString("openTime");
		String[] time = openTime.split(" - ");

		Time openingTime = null;
		Time closeTime = null;
		// 设备开放时间——闭时间
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		java.util.Date d1 = null;
		java.util.Date d2 = null;
		try {
			d1 = format.parse(time[0]);
			d2 = format.parse(time[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		openingTime = new java.sql.Time(d1.getTime());
		closeTime = new java.sql.Time(d2.getTime());

		// 设备类型
		String type = params.getString("type");

		// 设备单价
		double price = params.getDoubleValue("price");

		// 是否需要申请
		boolean isNeedApply = false;

		Equipment equipment = new Equipment(equipmentId, userId, equipmentName, equipmentDescrip, equipmentCapacity,
				status, positionProvince, positionCity, positionSpecificLocation, openingTime, closeTime, type, price,
				isNeedApply);

		return equipment;
	}

	/**
	 * 根据用户ID查找属于他的设备/空间
	 */
	@Override
	public void getEquipmentByUserId(Map<String, Object> map, HttpSession session) {
		// 获取用户ID
		String userId = (String) session.getAttribute("userId");
		List<Equipment> equipments = equipmentMapper.getEquipmentByUserId(userId);
		map.put("equipments", equipments);
	}

	@Override
	public void deleteEquipment(JSONObject params) {
		String equipmentId = params.getString("deleteEquipmentId");
		// 根据设备ID删除设备
		equipmentMapper.deleteEuipmentById(equipmentId);
		// 从发布申请中删除对应申请
		applyMapper.deleteApply(equipmentId);
	}

	/**
	 * 获取设备编辑前的信息
	 */
	@Override
	public void getOldEquipment(String equipmentId, Map<String, Object> map) {
		// 获取设备表的信息
		Equipment equipment = equipmentMapper.getEquipmentById(equipmentId);
		// 放到map中
		map.put("oldEquipment", equipment);

		// 获取该设备的所有图片，保存在map中
		List<EquipmentImage> equipmentImage = equipmentImageMapper.gEquipmentImages(equipmentId);
		map.put("oldEquipmentImage", equipmentImage);
	}

	@Override
	public void updateEquipment(JSONObject params) {
		JSONObject inputJsonData = JSONObject.parseObject(params.getString("inputDate"));
		// 获取更新后的数据
		Equipment equipment = getNewEquipmentInfo(inputJsonData);
		// 插入到数据库
		equipmentMapper.updateEquipment(equipment);

		// 向数据库保存设备图片信息——先获取image对象，然后将其insert如image表中
		// 路径字符串
		String imageUrls = params.getString("imageUrl");
		// 构造image对象
		List<EquipmentImage> equipmentImages = setEquipmentImage(equipment, imageUrls);
		// 依次将对象插入数据库
		for (EquipmentImage equipmentImage : equipmentImages) {
			equipmentImageMapper.insertEquipmentImage(equipmentImage);
		}

	}

	private Equipment getNewEquipmentInfo(JSONObject params) {
		// 设备编号
		// String equipmentId = UUID.randomUUID().toString().replaceAll("-", "");
		String equipmentId = params.getString("equipmentId");
		// 设备拥有者ID
		// String userId = null;
		// 设备名
		String equipmentName = params.getString("equipmentName");
		// 设备描述信息
		String equipmentDescrip = params.getString("description");
		// 设备容纳人数
		int equipmentCapacity = params.getInteger("equipmentCapacity");
		// 设备状态
		String status = params.getString("status");
		// 设备地址
		// 设备地址——省
		// String positionProvince = params.getString("positionProvince");

		// 设备地址——市
		// String positionCity = params.getString("positionCity");

		// 设备地址——具体位置
		// String positionSpecificLocation =
		// params.getString("positionSpecificLocation");

		String openTime = params.getString("openTime");
		String[] time = openTime.split(" - ");

		Time openingTime = null;
		Time closeTime = null;
		// 设备开放时间——闭时间
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		java.util.Date d1 = null;
		java.util.Date d2 = null;
		try {
			d1 = format.parse(time[0]);
			d2 = format.parse(time[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		openingTime = new java.sql.Time(d1.getTime());
		closeTime = new java.sql.Time(d2.getTime());

		// 设备类型
		String type = params.getString("type");

		// 设备单价
		double price = params.getDoubleValue("price");

		// 是否需要申请
		// boolean isNeedApply = false;

		Equipment equipment = new Equipment(equipmentId, equipmentName, equipmentDescrip, equipmentCapacity, status,
				openingTime, closeTime, type, price);

		return equipment;
	}

	@Override
	public void searchEquipment(HttpServletRequest request, Map<String, Object> map, HttpSession session) {
		//获取用户ID
		String userId=(String) session.getAttribute("userId");
		//获取前端查找的设备/空间名
		String equipmentName=(String) request.getParameter("ESname");
		System.out.println("获取到的查找设备名为："+equipmentName);
		List<Equipment> equipments=null;
		//如果有值则查找，没有值则显示所有
		if(!("".equals(equipmentName))) {
			Equipment equipment=new Equipment(userId, equipmentName);
			//查找出相应的设备
			equipments=equipmentMapper.searchEquipment(equipment);
		}
		else {
			equipments = equipmentMapper.getEquipmentByUserId(userId);
		}
		//放置在map中
		map.put("equipments", equipments);
	}

}
