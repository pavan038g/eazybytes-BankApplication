package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Schema(
		name="ResponseDto",
		description="Schema to hold Response information"
		)
public class ResponseDto {
	

	@Schema(
			description="Status code in the response"
			)
	private String statusCode;
	
	
	@Schema(
			description="Status message in the response"
			)
	private String statusMsg;
	
	

}
