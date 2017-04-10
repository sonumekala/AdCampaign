package com.comcast.campaign.exception;


public class DuplicateKeyException extends Exception {
	
	/**
	 * @author Sonu Mekala
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateKeyException(String message) {
		super(message);
	}

}
