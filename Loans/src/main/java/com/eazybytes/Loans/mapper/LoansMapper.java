package com.eazybytes.Loans.mapper;

import com.eazybytes.Loans.dto.LoansDto;
import com.eazybytes.Loans.entity.Loans;

public class LoansMapper {
	
	public static Loans mapToLoans(Loans loans, LoansDto loansDto) {
		
		loans.setAmmountPaid(loansDto.getAmmountPaid());
		loans.setMobileNumber(loansDto.getMobileNumber());
		loans.setLoanNumber(loansDto.getLoanNumber());
		loans.setLoanType(loansDto.getLoanType());
		loans.setOutstandingAmount(loansDto.getOutstandingAmount());
		loans.setTotalLoan(loansDto.getTotalLoan());
		
		return loans;	
		
	}
	
public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
		
		loansDto.setAmmountPaid(loans.getAmmountPaid());
		loansDto.setMobileNumber(loans.getMobileNumber());
		loansDto.setLoanNumber(loans.getLoanNumber());
		loansDto.setLoanType(loans.getLoanType());
		loansDto.setOutstandingAmount(loans.getOutstandingAmount());
		loansDto.setTotalLoan(loans.getTotalLoan());
		
		return loansDto;	
		
	}

}
