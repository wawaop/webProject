package com.guet.graduation.cfq.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.guet.graduation.cfq.dao.DataAnalysisMapper;
import com.guet.graduation.cfq.dao.EquipmentMapper;
import com.guet.graduation.cfq.dao.OrderMapper;
import com.guet.graduation.cfq.entity.DateAndUserId;
import com.guet.graduation.cfq.entity.Equipment;
import com.guet.graduation.cfq.entity.EquipmentIdAndTime;
import com.guet.graduation.cfq.entity.EquipmentIdAndTimes;
import com.guet.graduation.cfq.entity.EquipmentUseTimes;
import com.guet.graduation.cfq.entity.ExcelFee;
import com.guet.graduation.cfq.service.DataAnalysisService;

import net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForRawType;

@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {

	@Autowired
	private EquipmentMapper equipmentMapper;

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private DataAnalysisMapper dataAnalysisMapper;

	@Override
	public JSONObject getAnalysisData(JSONObject params, HttpSession session) {
		// 得到的开始日期
		String starDate = params.getString("startDate");
		// 结束日期
		String endDate = params.getString("endDate");
		// 根据用户ID获取属于他的设备，得到设备ID
		String userId = (String) session.getAttribute("userId");
		List<Equipment> equipments = equipmentMapper.getEquipmentByUserId(userId);
		
		//获取折线图数据（使用次数的数据）
		JSONObject jsonLineData = getJSONLineData(equipments, starDate, endDate);
		//获取饼状图数据（所有设备的收入数据）
		JSONObject jsonPieData= getJSONPieDate(equipments,starDate,endDate);
		
		//将折线图和饼状图的JSON数据返回
		JSONObject jObject=new JSONObject();
		jObject.put("jsonLineData", jsonLineData);
		jObject.put("jsonPieData", jsonPieData);
		
		return jObject;
	}

	
	/**
	 * 获取饼状图数据
	 * @param equipments
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	private JSONObject getJSONPieDate(List<Equipment> equipments, String starDate, String endDate) {
		//饼状图的整体JSOn
		JSONObject jObject=new JSONObject();
		
		//饼状图的legned需要的所有的设备的名
		List<String> equipmentList=new ArrayList<String>();
		
		//饼状图的series数组——数组中保存这设名，以及这段时间内的总价之和
		JSONArray jsonArray=new JSONArray();
		for(Equipment equipment : equipments) {
			//获取equipment的name
			equipmentList.add(equipment.getEquipmentName());
			
			//series数组中每个JSOn的形式为：value:总价，name:设备名
			JSONObject jsonObject=new JSONObject();
			//获取当前设备在时间段内的总价
			EquipmentIdAndTimes equipmentIdAndTimes=new EquipmentIdAndTimes(equipment.getEquipmentId(), starDate, endDate);
			Float sum=orderMapper.getSumByEidAndTimes(equipmentIdAndTimes);
			if(sum==null) {
				sum=(float) 0;
			}
			jsonObject.put("value", sum);
			jsonObject.put("name", equipment.getEquipmentName());
			//把当前JSon对象加入到JSON数组中
			jsonArray.add(jsonObject);
		}
		//构造饼状图需要的其他数据
		jObject.put("legend", equipmentList);
		//将series需要的数组加入到返回的对象中
		jObject.put("series", jsonArray);
		
		return jObject;
	}

	/**
	 * 获取折线图数据
	 * 
	 * @param equipments
	 * @return
	 */
	private JSONObject getJSONLineData(List<Equipment> equipments, String startDate, String endDate) {
		//整体返回的data（fastjson构造）
		JSONObject data=new JSONObject();
		
		//EChar需要的legend（即有多少个设备）——设备数组
		List<String> equipmentList=new ArrayList<String>();
		
		//构造ECHart需要的JSON数组(fastjson)
		JSONArray jsonArray=new JSONArray();
		
		// 遍历equipment
		for (Equipment equipment : equipments) {
			//获取设备数组
			equipmentList.add(equipment.getEquipmentName());
			
			//series里面的一个对象（fastjson构造）
			JSONObject jObject=new JSONObject();
			jObject.put("name", equipment.getEquipmentName());
			jObject.put("type", "line");
			jObject.put("stack", equipment.getEquipmentId());
			List<Integer> count = getCount(startDate, endDate, equipment);
			jObject.put("data", count);
			
			//将当前对象存放到JSOn数组中
			jsonArray.add(jObject);
		}
		
		// X轴的坐标——日期数组
		// 转换日期格式
		java.sql.Date starD = changeToSQLDate(startDate);
		java.sql.Date endD = changeToSQLDate(endDate);
		List<String> xAxis = dateUtil(starD, endD);

		//构造整体的返回的json的对象
		data.put("legend", equipmentList);
		data.put("xAxis", xAxis);
		data.put("series", jsonArray);
		
		// 返回给前端
		return data;
	}

	/**
	 * 将字符串日期转换为sql.data格式
	 * @param tempDate
	 * @return
	 */
	private Date changeToSQLDate(String tempDate) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = formatDate.parse(tempDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date returnDate = new java.sql.Date(d.getTime());
		return returnDate;
	}

	/**
	 * 获取指定时间段内的某设备每天的使用次数的数组
	 * 
	 * @param startDate
	 * @param endDate
	 * @param equipment
	 * @return
	 */
	private List<Integer> getCount(String startDate, String endDate, Equipment equipment) {
		// 指定时间段内，在订单表中找每天的总金额，然后除以单价，得到使用的半小时数
		// 转换日期格式
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		java.util.Date d2 = null;
		try {
			d = formatDate.parse(startDate);
			d2 = formatDate.parse(endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Date starD = new java.sql.Date(d.getTime());
		java.sql.Date endD = new java.sql.Date(d2.getTime());
		// 获取时间段内的所有时间
		List<String> alldate = dateUtil(starD, endD);

		// 使用的次数（每次0.5小时）
		List<Integer> count = new ArrayList<Integer>();
		// 查找订单中已付款的当天的该设备的所有总价
		for (String currentDate : alldate) {
			String equipmentId = equipment.getEquipmentId();
			EquipmentIdAndTime equipmentIdAndTime = new EquipmentIdAndTime(equipmentId, currentDate);
			Double sum = orderMapper.getSumPriceByEIdAndTime(equipmentIdAndTime);
			if (sum == null) {
				count.add(0);
			} else {
				// 当sum不为空的时候，用总价除以单价得到使用的次数
				double unitPrice = equipment.getPrice();
				int tempCount = (int) (sum / unitPrice);
				count.add(tempCount);
			}
		}
		return count;
	}

	/**
	 * 获取开始时间和结束时间内的所有日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private static List<String> dateUtil(Date start, Date end) {
		List<String> list = new ArrayList<String>();
		long s = start.getTime();
		long e = end.getTime();
		Long oneDay = 1000 * 60 * 60 * 24l;
		while (s <= e) {
			start = new Date(s);
			list.add(new SimpleDateFormat("yyyy-MM-dd").format(start));
			s += oneDay;
		}
		return list;
	}


	@Override
	public void exportData(String startDate,String endDate,HttpSession session,HttpServletResponse response) {
		
		String fname = "useData";// Excel文件名
		try {
			//正题——创建Excel HSSWorkbook————.xls
			Workbook wb = new HSSFWorkbook();
			//创建sheet页
			//使用次数
			Sheet useTimesSheet=wb.createSheet("使用次数");
			//收入
			Sheet feeSheet=wb.createSheet("设备收入情况");
			//向使用次数的sheet中添加数据
			setUseTimesSheet(useTimesSheet,startDate,endDate,session);
			//向收入sheet中添加数据
			setFeeSheet(feeSheet,startDate,endDate,session);
			
			//以上——创建Excel
			
			
			//输出
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + fname + ".xls"); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
			response.setContentType("application/msexcel");
			wb.write(os);
			os.flush();
			os.close();
			System.out.println("文件生成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		    
	}


	/**
	 * 向收入sheet中添加数据
	 * @param feeSheet
	 * @param startDate
	 * @param endDate
	 * @param session
	 */
	private void setFeeSheet(Sheet feeSheet, String startDate, String endDate, HttpSession session) {
		// 获取时间段内设备收入情况
		String userId=(String) session.getAttribute("userId");
		DateAndUserId dateAndUserId=new DateAndUserId(startDate, endDate, userId);
		List<ExcelFee> excelFees=dataAnalysisMapper.getFee(dateAndUserId);
		//设置表头
		feeSheet.setDefaultColumnWidth(22);
		Row row=feeSheet.createRow(0);
		row.createCell(0).setCellValue("设备名");
		row.createCell(1).setCellValue("设备收入");
		row.createCell(2).setCellValue("日期");
		int rowIndex=1;
		int cellIndex=0;
		for(ExcelFee excelFee : excelFees) {
			Row row2=feeSheet.createRow(rowIndex);
			row2.createCell(0).setCellValue(excelFee.getEquipmentName());
			row2.createCell(1).setCellValue(excelFee.getPrice());
			row2.createCell(2).setCellValue(startDate+"--"+endDate);
			rowIndex=rowIndex+1;
		}
	}


	/**
	 * 向sheet中插入数据——用户的设备在该时间段内的使用情况（次数）
	 * @param useTimesSheet
	 * @param startDate
	 * @param endDate
	 * @param session
	 */
	private void setUseTimesSheet(Sheet useTimesSheet, String startDate, String endDate, HttpSession session) {
		//获取在时间段内的设备的使用情况
		String userId=(String) session.getAttribute("userId");
		DateAndUserId dateAndUserId=new DateAndUserId(startDate, endDate, userId);
		List<EquipmentUseTimes> equipmentUseTimes=dataAnalysisMapper.getEquipmentUseTimes(dateAndUserId);
		//表头
		useTimesSheet.setDefaultColumnWidth(15);
		Row row=useTimesSheet.createRow(0);
		row.createCell(0).setCellValue("设备名");
		row.createCell(1).setCellValue("设备使用次数");
		row.createCell(2).setCellValue("日期");
		//然后将结果遍历插入sheet中
		int rowIndex=1;
		int cellIndex=0;
		for(EquipmentUseTimes equipmentUseTimes2 : equipmentUseTimes) {
			Row row2=useTimesSheet.createRow(rowIndex);
			row2.createCell(0).setCellValue(equipmentUseTimes2.getEquipmentName());
			row2.createCell(1).setCellValue(equipmentUseTimes2.getUseTimes());
			row2.createCell(2).setCellValue(equipmentUseTimes2.getUseDate());
			rowIndex=rowIndex+1;
		}
	}

}
