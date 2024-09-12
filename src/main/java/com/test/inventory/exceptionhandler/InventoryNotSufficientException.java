package com.test.inventory.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InventoryNotSufficientException extends RuntimeException {
	    public InventoryNotSufficientException(String message) {
	        super(message);
	    }
	    public InventoryNotSufficientException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
