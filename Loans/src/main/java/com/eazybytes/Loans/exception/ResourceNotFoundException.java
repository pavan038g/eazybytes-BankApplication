package com.eazybytes.Loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String resourcename,String fieldname,String fieldvalue) {
		super(String.format("%s not found with the given input data %s : '%s'", resourcename, fieldname, fieldvalue));
	}
}
