package com.eazybytes.Loans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Loans  extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	
	private String mobileNumber;
	
	
	private String loanNumber;
	
	
	private String loanType;
	
	
	private int totalLoan;
	
	
	private int ammountPaid;
	
	
	private int outstandingAmount;
	
	
	

}
