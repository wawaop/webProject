package com.guet.graduation.cfq.entity;

import java.sql.Date;

/**
 * 这个类是用于接收设备的开始时间和指定时间的
 * @author 123
 *
 */
public class StarTimeAndEndTime {
	Date startTime;
	
	Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public StarTimeAndEndTime(Date startTime, Date endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public StarTimeAndEndTime() {
	}
	
	
}
