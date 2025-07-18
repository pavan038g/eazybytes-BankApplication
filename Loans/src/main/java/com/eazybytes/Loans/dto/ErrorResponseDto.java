package com.eazybytes.Loans.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
	
	 @Schema(
	            description = "API path invoked by client"
	    )
	private String apiPath;
	
	 @Schema(
	            description = "Error code representing the error happened"
	    )
	private HttpStatus errorCode;
	
	 @Schema(
	            description = "Error message representing the error happened"
	    )
	private String errorMsg;
	
	 @Schema(
	            description = "Error time representing the error happened"
	    )
	private LocalDateTime errorTime;
	

}
