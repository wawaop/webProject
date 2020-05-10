package com.guet.graduation.cfq.entity;

public class Result<T> {

	private String code;
	private String msg;
	private T result;
	
	public Result(String code,String msg,T result) {
		this.code=code;
		this.msg=msg;
		this.result=result;
	}
	
	public Result(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
