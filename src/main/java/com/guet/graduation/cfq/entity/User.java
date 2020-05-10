package com.guet.graduation.cfq.entity;

import java.sql.Date;

public class User {
	
	String userId;
	
	int roleId;
	
	String login;
	
	String password;
	
	String imageUrl;
	
	String sex;
	
	Date birthday;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public User() {
	}
	
	public User(String login,String password) {
		this.login=login;
		this.password=password;
	}
	
	public User(String userId,int roleId,String login,String password) {
		this.userId=userId;
		this.roleId=roleId;
		this.login=login;
		this.password=password;
	}
	
	public User(String userId,int roleId,String login,String password,String imageUrl,String sex,Date birthday) {
		this.userId=userId;
		this.roleId=roleId;
		this.login=login;
		this.password=password;
		this.imageUrl=imageUrl;
		this.sex=sex;
		this.birthday=birthday;
	}
	
	public User(String userId, String sex, Date birthday) {
		super();
		this.userId = userId;
		this.sex = sex;
		this.birthday = birthday;
	}
	public User(String userId, String password, String sex, Date birthday) {
		super();
		this.userId = userId;
		this.password = password;
		this.sex = sex;
		this.birthday = birthday;
	}
	
	
}
