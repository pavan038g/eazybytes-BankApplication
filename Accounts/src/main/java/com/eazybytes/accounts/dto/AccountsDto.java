package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
		name="Accounts",
		description="Schema to hold Account information")

public class AccountsDto {
	
	@Schema(
			
			description="AccountNumber of the customer",example="8989898989"
			)
	@NotEmpty(message = "Account Number can not be null")
	@Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
	private Long accountNumber;
	

      @Schema(
			
			description="AccountType of the customer",example="SAVINGS"
			)
	@NotEmpty(message = "Account type cannot empty")
	private String accountType;
      @Schema(
  			
  			description="BranchAddress of the account",example="913_Newyork"
  			)
	@NotEmpty(message = "BranchAddress type cannot empty")
	private String branchAddress;

}
