package com.eazybytes.Loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "Loans",
description = "Schema to hold Loan information"
)
public class LoansDto {
	
	
	@NotEmpty(message = "Mobile number should not be empty")
	@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	 @Schema(
	            description = "Mobile Number of Customer", example = "4365327698"
	    )
	private String mobileNumber;
	
	
	@NotEmpty(message = "Loan number should be 12digits")
	@Pattern(regexp = "(^$|[0-9]{12})",message = "Loan number must be 12 digits")
	@Schema(
            description = "Loan Number of the customer", example = "548732457654"
    )
	private String loanNumber;
	
	
	@NotEmpty(message = "Loan type cannot be null or empty")
	@Schema(
            description = "Type of the loan", example = "Home Loan"
    )
	private String loanType;
	
	@Positive(message = "total Loan should be graater than zero")
	@Schema(
            description = "Total loan amount", example = "100000"
    )
	private int totalLoan;
	
	@PositiveOrZero(message = "amount should not be negative")
	@Schema(
            description = "Total loan amount paid", example = "1000"
    )
	private int ammountPaid;
	
	
	@PositiveOrZero(message = "outstanding amound should not be negative")
	@Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
	private int outstandingAmount;

}
