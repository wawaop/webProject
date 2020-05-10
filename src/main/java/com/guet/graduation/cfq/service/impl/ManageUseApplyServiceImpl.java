package com.guet.graduation.cfq.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guet.graduation.cfq.dao.ApplyForUseMapper;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.dao.OrderMapper;
import com.guet.graduation.cfq.dao.UserMapper;
import com.guet.graduation.cfq.entity.ApplyForUse;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.Order;
import com.guet.graduation.cfq.entity.UseApplyInfo;
import com.guet.graduation.cfq.entity.User;
import com.guet.graduation.cfq.service.ManageUseApplyService;
import com.guet.graduation.cfq.util.SymmetricEncoder;

@Service
public class ManageUseApplyServiceImpl implements ManageUseApplyService{

	@Autowired
	private ApplyForUseMapper applyForUseMapper;
	
	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public String getAllUseApplication(Map<String, Object> map) {
		//从数据库获取所有的正在申请的使用申请
		List<ApplyForUse> applyForUses=applyForUseMapper.getAllUseApplication();
		//将返回的数据整理成界面显示需要的内容
		List<UseApplyInfo> useApplyInfos=getUseApplyInfo(applyForUses);
		//将得到的界面信息放置在map中
		map.put("useApplyInfos", useApplyInfos);
		return "查找成功";
	}

	/**
	 * 将使用申请表中的数据整理成界面需要展示的内容
	 * @param applyForUses
	 * @return
	 */
	private List<UseApplyInfo> getUseApplyInfo(List<ApplyForUse> applyForUses) {
		//返回的显示数据List
		List<UseApplyInfo> useApplyInfos=new ArrayList<UseApplyInfo>();
		//一条条处理从数据库得到的数据
		for(ApplyForUse applyForUse: applyForUses) {
			//申请编号
			String apply_id=applyForUse.getApply_id();
			
			String equipmentId=applyForUse.getEquipment_id();
			Equipment equipment=equipmentMapper.getEquipmentById(equipmentId);
			//设备名
			String equipmentName=equipment.getEquipmentName();
			//设备种类
			String type=equipment.getType();
			
			//申请人
			String userId=applyForUse.getUserId();
			String login=SymmetricEncoder.AESDncode(userMapper.getUserNameById(userId));
			
			//申请提出时间
			Timestamp apply_date=applyForUse.getApply_date();
			
			//申请的使用日期
			Date useTime=applyForUse.getUseTime();
			
			//申请的使用时刻
			Time starTime=applyForUse.getStar_time();
			Time endTime=applyForUse.getEnd_time();
			//转为字符串
			String timeStamp=starTime.toString()+" - "+endTime.toString();
			
			//单价
			double unitPrice=equipment.getPrice();
			
			//总价
			double i = starTime.getTime();
	        double j = endTime.getTime();
	        double d = ((j - i) / 1800000.0);
			double price=d * unitPrice;
			
			//构成一个界面信息
			UseApplyInfo useApplyInfo=new UseApplyInfo(apply_id, equipmentName, type, login, apply_date, useTime, timeStamp, unitPrice, price);
			
			//添加到Info的List中
			useApplyInfos.add(useApplyInfo);
		}
		return useApplyInfos;
	}

	@Override
	public String passUseApply(JSONObject params) {
		//获取申请ID
		String apply_id=params.getString("passID");
		//判断待处理的使用申请在数据库中是否有冲突，如果没有则执行下面那些，有就返回有冲突
		//通过passId获取申请详细信息
		ApplyForUse applyForUse=applyForUseMapper.getApplyForUseById(apply_id);
		//判断同一设备、同一天，待处理的时刻是否和已通过的时刻冲突
		List<ApplyForUse> conflicApply=applyForUseMapper.getConflicApply(applyForUse);
		if(conflicApply.size()==0) {
			//如果返回的冲突申请为空，则说明没有冲突的申请，可以通过
			//mapper中修改数据表的申请状态为已通过
			applyForUseMapper.updateApplyStatusToPass(apply_id);
			//并且将通过的申请信息插入到订单表中，待后续付款
			//构建订单信息
			Order order=getOrderInfo(apply_id);
			//插入订单数据表
			orderMapper.insertOrder(order);
			return "操作成功";
		}else {
			//如果返回的冲突申请不为空，则不能给当前的申请通过，给前端返回提示
			return "申请时刻与已通过的申请存在冲突";
		}
		
		
	}

	/**
	 * 通过申请ID获取其他信息，构建订单表需要的信息内容，将通过使用申请的申请，插入订单表中
	 * @param apply_id
	 * @return
	 */
	private Order getOrderInfo(String apply_id) {
		//通过apply_id获取申请表中的该条记录
		ApplyForUse applyForUse=applyForUseMapper.getApplyForUseById(apply_id);
		
		//构建订单信息
		//订单编号
		String order_id=UUID.randomUUID().toString().replaceAll("-", "");
		//用户ID
		String userId=applyForUse.getUserId();
		//设备编号
		String equipment_id=applyForUse.getEquipment_id();
		//提交预定的时间——申请使用的申请提交时间
		Timestamp order_date=applyForUse.getApply_date();
		//订单中的开始时刻
		Time star_time=applyForUse.getStar_time();
		//订单中的结束时刻
		Time end_time=applyForUse.getEnd_time();
		//订单中的使用的日期
		Date useTime=applyForUse.getUseTime();
		
		//计算订单总价
		Equipment equipment=equipmentMapper.getEquipmentById(equipment_id);
		double unitPrice=equipment.getPrice();
		double i = star_time.getTime();
        double j = end_time.getTime();
        //0.5小时为计算，所以用的是1800000
        double d = ((j - i) / 1800000.0);
		double price=d * unitPrice;
		
		//订单状态
		String order_status="待付款";
		
		//构建Order类
		Order order=new Order(order_id, userId, equipment_id, order_date, star_time, end_time, useTime, price, order_status);
		
		return order;
	}

	@Override
	public String unPassUseApply(JSONObject params) {
		//获取申请的ID
		String apply_id=params.getString("unPassID");
		//修改表中的申请状态为不通过
		applyForUseMapper.updateApplyStatusToUnPass(apply_id);
		return "操作成功";
	}

}
