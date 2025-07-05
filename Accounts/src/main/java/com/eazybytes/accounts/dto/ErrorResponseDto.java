package com.eazybytes.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@NoArgsConstructor@Data
@Schema(
		name="ErrorResponseDto",
		description="Schema to hold ErrorResponseDto"
		)
public class ErrorResponseDto {
	
	@Schema(
			description="api path by client"
			)
	private String apiPath;
	@Schema(
			description="Error code representing the error happened"
			)
	private HttpStatus errorCode;
	
	@Schema(
			description="Error meesage representing the error happened"
			)
	private String errorMessage;
	@Schema(
			description="Error time representing the error happened"
			)
	private LocalDateTime errorTime;

}
