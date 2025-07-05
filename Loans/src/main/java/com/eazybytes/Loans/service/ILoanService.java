package com.eazybytes.Loans.service;

import com.eazybytes.Loans.dto.LoansDto;

public interface ILoanService {
	
	void createLoan(String mobileNumber);

	LoansDto fetchLoanDetails(String mobileNumber);

	boolean updateLoanDetails(LoansDto loansDto);

	boolean deleteLoanDetails(String mobileNumber);
}
