package com.guet.graduation.cfq.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guet.graduation.cfq.dao.ApplyMapper;
import com.guet.graduation.cfq.dao.EquipmentImageMapper;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.dao.UserMapper;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.EquipmentImage;
import com.guet.graduation.cfq.entity.PublishApply;
import com.guet.graduation.cfq.entity.PublishApplyInfo;
import com.guet.graduation.cfq.service.ApplyService;
import com.guet.graduation.cfq.util.SymmetricEncoder;

@Service
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Autowired
	private EquipmentImageMapper equipmentImageMapper;

	/**
	 * 获取所有需要处理的设备发布申请
	 */
	@Override
	public void getPublisApply(Map<String, Object> map) {
		// 获取所有需要处理的设备发布申请放置到 map中
		List<PublishApply> publishApplys = applyMapper.getPublishApply();
		List<PublishApplyInfo> publishApplyInfos=new ArrayList<PublishApplyInfo>();
		for (PublishApply publishApply : publishApplys) {
			// 通过获取到的userId获取到用户名（要解密）
			String userNameEncode = userMapper.getUserNameById(publishApply.getUserId());
			String userName=SymmetricEncoder.AESDncode(userNameEncode);
			// 通过获取到的equipmentId获取到所有的设备信息
			Equipment equipment=equipmentMapper.getEquipmentById(publishApply.getEquipmentId());
			// 将获取到的申请对象、用户名、设备对象，封装到PublishApplyInfo类中
			PublishApplyInfo publishApplyInfo=new PublishApplyInfo(userName,publishApply,equipment);
			// 将封装的类放置到publishApplyInfo的list中
			publishApplyInfos.add(publishApplyInfo);
		}
		//将得到的所有信息放置在map中
		map.put("publishApplyInfos", publishApplyInfos);

	}

	@Override
	public void getDetailApplyById(String applyId, Map<String, Object> map) {
		//根据得到的AppLyID获取相应的申请信息
		PublishApply publishApply=applyMapper.getPublishApplyById(applyId);
		//根据得到的申请信息，获取用户名
		String userNameEcode=userMapper.getUserNameById(publishApply.getUserId());
		String userName=SymmetricEncoder.AESDncode(userNameEcode);
		//根据得到的申请新，获取设备信息
		Equipment equipment=equipmentMapper.getEquipmentById(publishApply.getEquipmentId());
		//根据得到的申请信息，获取设备图片
		List<EquipmentImage> equipmentImages=equipmentImageMapper.gEquipmentImages(publishApply.getEquipmentId());
		//组装成PublishApplyInfo
		PublishApplyInfo publishApplyInfo=new PublishApplyInfo(userName,publishApply,equipment,equipmentImages);
		//将得到的所有信息放置在map中
		map.put("applyDetail", publishApplyInfo);
	}

	/**
	 * 通过发布申请
	 */
	@Override
	public void passApply(JSONObject params) {
		String applyId=params.getString("passID");
		//修改发布申请表中该申请编号的信息为“已通过”
		applyMapper.passApply(applyId);
		//获取申请编号对应的设备ID
		String equipmentId=applyMapper.getEquipmentIdById(applyId);
		//将设备ID对应的设备状态修改为“可使用”
		equipmentMapper.updateEquipmentStatus(equipmentId);
	}

	/**
	 * 处理不通过的申请，将申请信息状态为不通过
	 */
	@Override
	public void unPassApply(JSONObject params) {
		String unPassId=params.getString("unPassID");
		//修改申请状态为不通过
		applyMapper.unPassApply(unPassId);
	}

	@Override
	public void myPublishApply(HttpSession session, Map<String, Object> map) {
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		List<PublishApplyInfo> publishApplyInfos=new ArrayList<PublishApplyInfo>();
		//获取发布信息
		List<PublishApply> publishApplies=applyMapper.getMyPublishApply(userId);
		for(PublishApply publishApply : publishApplies) {
			//通过发布申请中的设备ID获取设备信息
			Equipment equipment=equipmentMapper.getEquipmentById(publishApply.getEquipmentId());
			//组装成PublishApplyInfo
			PublishApplyInfo publishApplyInfo=new PublishApplyInfo(null,publishApply,equipment);
			publishApplyInfos.add(publishApplyInfo);
		}
		//将List放置在map中
		map.put("myPublishApplys", publishApplyInfos);
	}

	@Override
	public void myPublishApply(HttpSession session, Map<String, Object> map, Integer pageNum, Integer pageSize,
			Integer applyPageNum, Integer applyPageSize, Integer passPageNum, Integer passPageSize,
			Integer unPassPageNum, Integer unPassPageSize, Integer tabNum) {
		// 用户ID
		String userId = (String) session.getAttribute("userId");
		
		//获取所有的申请信息
		PageHelper.startPage(pageNum, pageSize);
		List<PublishApply> publishApplies=applyMapper.getMyPublishApply(userId);
		PageInfo<PublishApply> publishApplyiesPageInfo=new PageInfo<PublishApply>(publishApplies, pageSize);
		map.put("publishApplliesPageInfo", publishApplyiesPageInfo);
		
		//获取申请中的信息
		PageHelper.startPage(applyPageNum, applyPageSize);
		List<PublishApply> applying=applyMapper.getApplying(userId);
		PageInfo<PublishApply> applyingInfo=new PageInfo<PublishApply>(applying, applyPageSize);
		map.put("applyingInfo", applyingInfo);
		
		//获取“已通过"的信息
		PageHelper.startPage(passPageNum, passPageSize);
		List<PublishApply> passApply=applyMapper.getPassApply(userId);
		PageInfo<PublishApply> passApplyPageInfo=new PageInfo<PublishApply>(passApply, passPageSize);
		map.put("passApplyPageInfo", passApplyPageInfo);
		
		//获取“不通过”的信息
		PageHelper.startPage(unPassPageNum, unPassPageSize);
		List<PublishApply> unPassApply=applyMapper.getUnPassApply(userId);
		PageInfo<PublishApply> unPassPageInfo=new PageInfo<PublishApply>(unPassApply, passPageSize);
		map.put("unPassPageInfo", unPassPageInfo);
		
		map.put("applyTabNum", tabNum);
	}


}
