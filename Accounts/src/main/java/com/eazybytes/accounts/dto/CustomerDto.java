package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
		name="Customer",
		description="Schema to hold Customer and Account information"
		)
public class CustomerDto {
	
	@Schema(
	
			description="Name of the customer",example="Eazy Bytes"
			)
	@NotEmpty(message="Name cannot be empty")
	@Size(min=5,max=30,message="The name should be between the length of 5 and 30")
    private String name;
	
	@Schema(
			
			description="Email of the customer",example="EazyBytes@gmail.com"
			)
	@NotEmpty(message="email address cannot be empty")
	@Email(message="Email address should be valid value")
	private String email;

	@Schema(
			
			description="MobileNumber of the customer",example="9999999999"
			)
	@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	private String mobileNumber;
	
	@Schema(
			description = "Account details of customer")
	private AccountsDto accountDto;

}
