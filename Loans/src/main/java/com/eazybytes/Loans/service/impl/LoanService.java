package com.eazybytes.Loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.Loans.constants.LoanConstants;
import com.eazybytes.Loans.dto.LoansDto;
import com.eazybytes.Loans.entity.Loans;
import com.eazybytes.Loans.exception.LoanAlreadyExistException;
import com.eazybytes.Loans.exception.ResourceNotFoundException;
import com.eazybytes.Loans.mapper.LoansMapper;
import com.eazybytes.Loans.repository.LoansRepository;
import com.eazybytes.Loans.service.ILoanService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanService implements ILoanService {
	
	private LoansRepository loansRepository;

	@Override
	public void createLoan(String mobileNumber) {
		Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
		
		if(optionalLoans.isPresent()) {
			throw new LoanAlreadyExistException("mobile number already exist "+mobileNumber);
		}
		loansRepository.save(createNewLoan(mobileNumber));
	}
	
	private Loans createNewLoan(String mobileNumber) {
		
		 Loans newLoan = new Loans();
	        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
	        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
	        newLoan.setMobileNumber(mobileNumber);
	        newLoan.setLoanType(LoanConstants.HOME_LOAN);
	        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
	        newLoan.setAmmountPaid(0);
	        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
	        return newLoan;
	}

	@Override
	public LoansDto fetchLoanDetails(String mobileNumber) {
		Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Loans","mobileNumber",mobileNumber.toString()));
		return LoansMapper.mapToLoansDto(loans, new LoansDto());
		
	}

	@Override
	public boolean updateLoanDetails(LoansDto loansDto) {
		Loans loans=loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
				()-> new ResourceNotFoundException("Loans", "LoanNumber", loansDto.getLoanNumber().toString()));
		
		LoansMapper.mapToLoans(loans, loansDto);
		loansRepository.save(loans);
		return true;
	}

	   @Override
	    public boolean deleteLoanDetails(String mobileNumber) {
	        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber.toString())
	        );
	        loansRepository.deleteById(loans.getLoanId());
	        return true;
	    }

}
