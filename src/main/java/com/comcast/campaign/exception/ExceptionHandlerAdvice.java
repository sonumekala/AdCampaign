package com.comcast.campaign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.comcast.campaign.vo.Error;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(NoActiveCampaignException.class)
    public ResponseEntity<Error> noActiveCampaignHandler(Exception ex) {
		Error error = new Error();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.OK);
    }
	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Error> duplicateKeyExceptionHandler(Exception ex) {
		Error error = new Error();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.OK);
    }
}
