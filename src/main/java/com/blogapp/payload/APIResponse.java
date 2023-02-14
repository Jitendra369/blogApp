package com.blogapp.payload;

import org.springframework.http.HttpStatus;

public class APIResponse {

	private String message;
	private HttpStatus httpStatus;
	private boolean success;
	public APIResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public APIResponse(String message, HttpStatus httpStatus, boolean success) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.success = success;
	}
	
	
	public APIResponse(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
