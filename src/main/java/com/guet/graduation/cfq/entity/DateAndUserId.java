package com.guet.graduation.cfq.entity;

/**
 * 该类用于构建数据分析导出Excel表时使用次数的MyBatis的参数
 * @author 123
 *
 */
public class DateAndUserId {
	
	String startDate;
	
	String endDate;
	
	String userId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public DateAndUserId(String startDate, String endDate, String userId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
	}

	public DateAndUserId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
