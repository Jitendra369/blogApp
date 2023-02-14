package com.blogapp.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private String resourceName;
	private String fieldName;
	private int feildValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, int feildValue) {
		
		super(String.format("%s not found with %s : %s", resourceName, fieldName, feildValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.feildValue = feildValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFeildValue() {
		return feildValue;
	}

	public void setFeildValue(int feildValue) {
		this.feildValue = feildValue;
	}
	
	
	
}
