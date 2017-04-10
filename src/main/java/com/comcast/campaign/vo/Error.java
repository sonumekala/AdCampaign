package com.comcast.campaign.vo;

/**
 * 
 * @author Sonu Mekala
 *
 */
public class Error {
	
	private String message;
	private int errorCode;
	
	public Error(){
		
	}
	
	public Error(String status, String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "Error [erroCode=" + errorCode + ", message=" + message + "]";
	}
	
	

}
