package com.eazybytes.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsContactsInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
		name="CRUD REST APIs for Account in EazyBank",
		description="CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
		)
public class AccountController {
	
	private IAccountService iAccountService;
	
	
	public AccountController(IAccountService iAccountService) {
		this.iAccountService=iAccountService;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private  AccountsContactsInfoDto accountsContactsInfoDto;
	
	
	
	@Operation(
			summary="create Account REST API",
			description="REST API to create new customer and Account inside EazyBytes")
	@ApiResponses({
		@ApiResponse(
				responseCode = "201",
				description = "HTTP status Created"
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
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
		
		iAccountService.createAccount(customerDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
		
	}
	
	@Operation(
			summary="fetch Account REST API",
			description="REST API to fetch Account details using mobileNumber")
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
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam 
			@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits") 
	        String mobileNumber){
		CustomerDto customerDto=iAccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}
	
	@Operation(
			summary="update Account REST API",
			description="REST API to update Account details using accountNumber")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP status Ok"
					),
			@ApiResponse(
					responseCode = "417",
					description = "Exception Failed"
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
	@PatchMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
		boolean isUpdated=iAccountService.updateAccount(customerDto);
		
		if(isUpdated) {
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
		}
		else {
			
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_UPDATE));
		}
	}
	
	@Operation(
			summary="Delete Account REST API",
			description="REST API to Delete Account details using mobileNumber")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP status Ok"
					),
			@ApiResponse(
					responseCode = "417",
					description = "Exception Failed"
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
	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam 
			@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	        String mobileNumber){
		
		boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
		}
		else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_DELETE));
		}
	}
	
	
	@Operation(
			summary="Get Build Information",
			description="Get Build information that is deployed into accounts microservice")
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

	@Retry(name="getBuildInfo",fallbackMethod = "getBuildInfoFallback")
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo(){
		
		logger.debug("get Build Info() method Invoked");

		throw new NullPointerException();
//		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}
	
	
	public ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
		logger.debug("get Build Fallback() method Invoked");
		
		return ResponseEntity.status(HttpStatus.OK).body("0.9");
	}
	
	
	
	@Operation(
			summary="Get JAVA Version",
			description="Get Java version that is deployed into accounts microservice")
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

	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion(){
		
		
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
	}
	
	@Operation(
			summary="Get contact info",
			description="Get contact info details that is deployed into accounts microservice")
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

	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactsInfoDto> getContactInfo(){
		
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactsInfoDto);
	}
	

}
