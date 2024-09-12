package com.test.inventory.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PartNumberDontExistException extends RuntimeException {
	    public PartNumberDontExistException(String message) {
	        super(message);
	    }
	    public PartNumberDontExistException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
