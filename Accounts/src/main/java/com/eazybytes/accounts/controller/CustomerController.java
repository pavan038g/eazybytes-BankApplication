package com.eazybytes.accounts.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
		name=" REST APIs for Customer in EazyBank",
		description=" REST APIs in EazyBank to fetch Customer details"
		)
public class CustomerController {
	
	private static final Logger logger =LoggerFactory.getLogger(CustomerController.class);
	private final ICustomerService iCustomerService;
	
	public CustomerController(ICustomerService iCustomerService) {
		this.iCustomerService=iCustomerService;
	}
	
	@Operation(
			summary="fetch Customer Details REST API",
			description="REST API to fetch Customer details using mobileNumber")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "HTTP status Ok"
				),
		@ApiResponse(
				responseCode = "500",
				description = "HTTP status Internal Server Error",
				content=@Content(
						schema = @Schema(implementation = ErrorResponseDto.class)
						)
				)
}
)
	@GetMapping("/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id") 
	       String correlationId,
			@RequestParam 
			@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits") 
    String mobileNumber ){
		
		logger.debug("EazyBank-coorelation-id found:{}",correlationId);
		CustomerDetailsDto customerDetailsDto=iCustomerService.fetchCustomerDetails(mobileNumber,correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
	}

}
