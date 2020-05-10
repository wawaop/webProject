package com.guet.graduation.cfq.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guet.graduation.cfq.dao.ApplyForUseMapper;
import com.guet.graduation.cfq.dao.EquipmentImageMapper;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.dao.UserMapper;
import com.guet.graduation.cfq.entity.ApplyForUse;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.EquipmentAndTimestamp;
import com.guet.graduation.cfq.entity.MyUseApply;
import com.guet.graduation.cfq.entity.StarTimeAndEndTime;
import com.guet.graduation.cfq.service.ApplyForUseService;

import ch.qos.logback.core.subst.Token.Type;

@Service
public class ApplyForUseServiceImpl implements ApplyForUseService {

	@Autowired
	private ApplyForUseMapper applyForUseMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Autowired
	private EquipmentImageMapper equipmentImageMapper;

	@Override
	public String searchKaZuo(JSONObject params, Map<String, Object> map) {
		// 获取地点和时间
		String positionProvince = params.getString("positionProvince");
		String positionCity = params.getString("positionCity");
		String useTime = params.getString("useTime");
		System.out.println("得到的json数据为：" + positionProvince + positionCity + useTime);
		// 通过省和市查找符合条件的所有的卡座（工位）
		Equipment equipment = new Equipment(positionProvince, positionCity, "工位");
		List<Equipment> equipments = applyForUseMapper.getEquipmentByPosition(equipment);
		System.out.println("得到的equipments为：" + equipments.get(0).getEquipmentName());
		map.put("equipmentsByPosition", equipments);
		return "成功";
	}

	/*
	 * @Override public String searchKaZuo(HttpServletRequest request, Map<String,
	 * Object> map) { // 获取地点和时间 String positionProvince =
	 * request.getParameter("positionProvince"); String positionCity =
	 * request.getParameter("positionCity"); // String useTime = (String)
	 * request.getAttribute("useTime"); System.out.println("得到的地点为：" +
	 * positionProvince + positionCity); //获取预定的类型 String
	 * type=request.getParameter("type"); //获取预定的时间 String
	 * useTime=request.getParameter("date");
	 * 
	 * // 通过省和市查找符合条件的所有的卡座（工位） // Equipment equipment = new
	 * Equipment(positionProvince, positionCity, "工位");
	 * 
	 * //通过省和市查找所有符合条件的该类型设备或空间 Equipment equipment=new Equipment(positionProvince,
	 * positionCity, type); List<Equipment> equipments =
	 * applyForUseMapper.getEquipmentByPosition(equipment);
	 * 
	 * // map.put("equipmentsByPosition", equipments);
	 * 
	 * //查找该设备在指定时间内“申请中”和“已通过”的所有的时间,并将其和Equipment放置在同一个Map对象中
	 * setEquipmentAndTime(equipments,useTime,map);
	 * 
	 * 
	 * return null; }
	 */

	@Override
	public String searchKaZuo(HttpServletRequest request, Map<String, Object> map) {
		// 获取地点和时间
		String positionProvince = request.getParameter("positionProvince");
		String positionCity = request.getParameter("positionCity");
		// String useTime = (String) request.getAttribute("useTime");
		System.out.println("得到的地点为：" + positionProvince + positionCity);
		// 获取预定的类型
		String type = request.getParameter("type");
		// 获取预定的时间
		String useTime = request.getParameter("date");

		// 通过省和市查找所有符合条件的该类型设备或空间
		Equipment equipment = new Equipment(positionProvince, positionCity, type);
		List<Equipment> equipments = applyForUseMapper.getEquipmentByPosition(equipment);

		// 查找该设备在指定时间内“申请中”和“已通过”的所有的时间,并将其和Equipment放置在同一个Map对象中
		setEquipmentAndTime(equipments, useTime, map);
		
		//将地点、类型、时间放到map中，方便前端显示
		map.put("positionProviceInfo", positionProvince);
		map.put("positionCityInfo", positionCity);
		map.put("typeInfo", type);
		map.put("dateInfo", useTime);
		
		return null;
	}

	/**
	 * 查找设备在某天中内的所有的预定时刻,并放置在map中
	 * 
	 * @param equipments
	 * @param map
	 */
	private void setEquipmentAndTime(List<Equipment> equipments, String useDate, Map<String, Object> map) {
		if (useDate == null) {
			return;
		}
		// 申请的使用日期
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = formatDate.parse(useDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date useTime = new java.sql.Date(d.getTime());
		
		//存放所有设备和其时刻
		List<EquipmentAndTimestamp> equipmentAndTimestamps=new ArrayList<EquipmentAndTimestamp>();

		// 获取该设备当天的所有“已通过”的开始时间和结束时间
		for (Equipment equipment : equipments) {
			EquipmentAndTimestamp equipmentAndTimestamp = new EquipmentAndTimestamp();
			List<String> passTimes=new ArrayList<String>();
			List<String> applyingTime=new ArrayList<String>();
			//获取“已通过”的时刻
			passTimes=getTimes(equipment,useTime,"已通过");
			
			//获取“申请中”的时刻
			applyingTime=getTimes(equipment, useTime, "申请中");
			
			//获取当前equipment的图片（只取一张）
			String imageUrl=equipmentImageMapper.getImageUrlById(equipment.getEquipmentId());
			
			
			//将设备、“已通过”的时刻、“申请中”的时刻,图片URL，加入到它本身的对象中
			equipmentAndTimestamp.setEquipment(equipment);
			equipmentAndTimestamp.setPassTime(passTimes.toString());
			equipmentAndTimestamp.setApplyingTime(applyingTime.toString());
			equipmentAndTimestamp.setImageUrl(imageUrl);
			
			//将对象添加到整体中
			equipmentAndTimestamps.add(equipmentAndTimestamp);
		}
		//将整体放置到map中
		map.put("EquipmentAndTimes", equipmentAndTimestamps);
		
		
		return;
	}
	
	/**
	 * 获取某设备的当天的某状态的所有开始时刻和结束时刻
	 * @param equipment
	 * @param useTime
	 * @param string
	 * @return
	 */
	private List<String> getTimes(Equipment equipment, java.sql.Date useTime, String status) {
		ApplyForUse passApplyForUse = new ApplyForUse(equipment.getEquipmentId(), useTime, status);
		List<ApplyForUse> applyForUses = applyForUseMapper.getAllStartTimeAndEndTime(passApplyForUse);
		List<String> allTimes=new ArrayList<String>();
		// 遍历所有的开始-结束 对应时刻，获取中间的所有时刻，放到返回值的passTime中
		for (ApplyForUse applyForUse : applyForUses) {
			if(applyForUse==null) {
				break;
			}
			// 获取中间时刻，放到时刻数组中
//			List<String> timeStamps = dateUtil(applyForUse.getStar_time(), applyForUse.getEnd_time());
			List<String> temp=dateUtil(applyForUse.getStar_time(), applyForUse.getEnd_time());
			if(temp.size()>1) {
				allTimes.addAll(temp.subList(0, temp.size()-1));
			}
			else {
				allTimes.addAll(temp);
			}
//			allTimes.addAll(timeStamps);
		}
		return allTimes;
	}

	/**
	 * 获取开始时间和结束时间内的所有时刻
	 * 
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	private static List<String> dateUtil(Date start, Date end) {
		List<String> list = new ArrayList<String>();
		long s = start.getTime();
		long e = end.getTime();
		Long oneDay = 1000 * 60 * 30l;
		while (s <= e) {
			start = new Date(s);
			list.add(new SimpleDateFormat("HH:mm").format(start));
			s += oneDay;
		}
		return list;
	}
	/*
	 * private static List<String> dateUtil(Date starTime, String endTime) {
	 * 
	 * SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	 * java.util.Date d = null; java.util.Date d2 = null; try { d =
	 * formatDate.parse(starTime); d2 = formatDate.parse(endTime); } catch
	 * (Exception e) { e.printStackTrace(); } java.sql.Date start = new
	 * java.sql.Date(d.getTime()); java.sql.Date end = new
	 * java.sql.Date(d2.getTime());
	 * 
	 * List<String> list = new ArrayList<String>(); long s = start.getTime(); long e
	 * = end.getTime(); Long oneDay = 1000 * 60 * 60 * 24l; while (s <= e) { start =
	 * new java.sql.Date(s); list.add(new
	 * SimpleDateFormat("yyyy-MM-dd").format(start)); s += oneDay; } return list; }
	 */

	@Override
	public String applyKaZuo(JSONObject params, HttpSession session) {
		// 获取apply_for_use表需要的东西
		ApplyForUse applyForUse = getApplyForUseInfo(params, session);
		
		//判断当前申请的设备在当天的时刻在“已通过”中是否有冲突，如果没有则可以申请，否则返回冲突提示
		List<ApplyForUse> conflixtApply=applyForUseMapper.getConflicApply(applyForUse);
		if(conflixtApply.size()==0) {
			// 将其插入到数据库中
			applyForUseMapper.insertApplyForUse(applyForUse);
			return "预定成功";
		}else {
			return "时刻已被预定，请重新刷新界面";
		}
		
		
	}

	/**
	 * 解析Json数据获取Apply_for_use需要的内容
	 * 
	 * @param params
	 * @return
	 */
	private ApplyForUse getApplyForUseInfo(JSONObject params, HttpSession session) {
		// 申请编号
		String apply_id = UUID.randomUUID().toString().replaceAll("-", "");
		// 获取设备编号
		String equipment_id = params.getString("equipmentId");
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		// 申请所提出的时间
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp apply_date = new Timestamp(currentTime.getTime());
		// 申请当前的状态
		String apply_status = "申请中";

		String info = params.getString("info");
		// 切割信息
		String[] timeInfo = info.split(" ");
		// 申请使用的开始、结束时刻
		String[] timestamp = timeInfo[1].split("-");
		Time star_time = null;
		Time end_time = null;
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
		java.util.Date d1 = null;
		java.util.Date d2 = null;
		try {
			d1 = formatTime.parse(timestamp[0]);
			d2 = formatTime.parse(timestamp[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		star_time = new java.sql.Time(d1.getTime());
		end_time = new java.sql.Time(d2.getTime());

		// 申请的使用日期
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = formatDate.parse(timeInfo[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date useTime = new java.sql.Date(d.getTime());
		// 申请原因
		String applyReason = null;

		ApplyForUse applyForUse = new ApplyForUse(apply_id, equipment_id, userId, apply_date, apply_status, star_time,
				end_time, useTime, applyReason);

		return applyForUse;
	}

	@Override
	public void myUseApply(HttpSession session, Map<String, Object> map, Integer pageNum, Integer pageSize,
			Integer applyPageNum, Integer applyPageSize, Integer passPageNum, Integer passPageSize,
			Integer unPassPageNum, Integer unPassPageSize, Integer tabNum) {
		List<MyUseApply> myUseApplies=new ArrayList<MyUseApply>();
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		
		//获取ID的所有使用申请
		PageHelper.startPage(pageNum, pageSize);
		List<ApplyForUse> applyForUses=applyForUseMapper.getMyApplyForUse(userId);
		PageInfo<ApplyForUse> pageInfo=new PageInfo<ApplyForUse>(applyForUses, pageSize);
		//添加到map中
		map.put("myUseAppliesInfo", pageInfo);
		
		//获取用户的“申请中”的使用申请
		PageHelper.startPage(applyPageNum, applyPageSize);
		List<ApplyForUse> applyingUse=applyForUseMapper.getApplyingUseByUseId(userId);
		PageInfo<ApplyForUse> applyingPageInfo=new PageInfo<ApplyForUse>(applyingUse, applyPageSize);
		//添加到map中
		map.put("applyingPageInfo", applyingPageInfo);
		
		//获取用户的“已通过”的使用申请
		PageHelper.startPage(passPageNum, passPageSize);
		List<ApplyForUse> passUseApplys=applyForUseMapper.getPassApplyByUseId(userId);
		PageInfo<ApplyForUse> passApplyPageInfo=new PageInfo<ApplyForUse>(passUseApplys, passPageSize);
		//添加到map中
		map.put("passApplyPageInfo", passApplyPageInfo);
		
		//获取用户的“不通过”的使用申请
		PageHelper.startPage(unPassPageNum, unPassPageSize);
		List<ApplyForUse> unPassApplys=applyForUseMapper.getUnPassApplyByUseId(userId);
		PageInfo<ApplyForUse> unPassApplysPageInfo=new PageInfo<ApplyForUse>(unPassApplys, unPassPageSize);
		//添加到map中
		map.put("unPassApplyPageInfo", unPassApplysPageInfo);
		
		map.put("userApplytabNum", tabNum);
		
	}

//	@Override
//	public void myUseApply(HttpSession session, Map<String, Object> map) {
//		List<MyUseApply> myUseApplies=new ArrayList<MyUseApply>();
//		// 用户ID
//		String userId = (String) session.getAttribute("userId");
//		//获取ID的所有使用申请
//		List<ApplyForUse> applyForUses=applyForUseMapper.getMyApplyForUse(userId);
//		for(ApplyForUse applyForUse : applyForUses) {
//			//对应申请的设备的信息
//			Equipment equipment=equipmentMapper.getEquipmentById(applyForUse.getEquipment_id());
//			MyUseApply myUseApply=new MyUseApply(applyForUse, equipment);
//			myUseApplies.add(myUseApply);
//		}
//		//添加到map中
//		map.put("myUseApplies", myUseApplies);
//	}

}
