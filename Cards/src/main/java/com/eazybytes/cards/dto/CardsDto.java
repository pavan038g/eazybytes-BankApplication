package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardsDto {
	
   
   @NotEmpty(message="mobileNumber for cards should not be empty")
   @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
   @Schema(
           description = "Mobile Number of Customer", example = "4354437687"
   )
   private String mobileNumber;
	
   @NotEmpty(message="cardNumber for cards should not be empty")
   @Pattern(regexp = "(^$|[0-9]{12})",message = "Account number must be 12 digits")
   @Schema(
           description = "Card Number of Customer", example = "101101010101"
   )
	private String cardNumber;
	
   @Schema(
           description = "Type of the card", example = "Credit Card"
   )
   @NotEmpty(message="cardNumber for cards should not be empty")
	private String cardType;
	
   @Schema(
           description = "Total amount limit available against a card", example = "100000"
   )
   @Positive(message="totalLimit should be always positive")
	private int totalLimit;
	
   @PositiveOrZero(message="amountUsed should not be negative ")
   @Schema(
           description = " amount used available against a card", example = "1000"
   )
	private int amountUsed;
   
   @PositiveOrZero(message="availableAmount should not be negative ")
   @Schema(
           description = "Available amount available against a card", example = "99000"
   )
	private int avaliableAmount;

}
