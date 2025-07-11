package com.eazybytes.cards.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.eazybytes.cards.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(CardAlreadyExistException.class)
	public ResponseEntity<ErrorResponseDto> handleCardAlreadyExist(CardAlreadyExistException exception,WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto=new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
		
	}

}
