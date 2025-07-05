package com.eazybytes.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eazybytes.accounts.Exception.ResourceNotFoundException;
import com.eazybytes.accounts.Mapper.AccountMapper;
import com.eazybytes.accounts.Mapper.CustomerMapper;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public  class CustomerServiceImpl  implements ICustomerService{
	
	private AccountsRepository accountsRepository;
	
	private CustomerRepository customerRepository;
	
	private CardsFeignClient cardsFeignClient;
	
	private LoansFeignClient loansFeignClient;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String coorelationId) {
		Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));
		Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()->new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
		
		CustomerDetailsDto customerDetailsDto=CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
		
		customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(accounts, new AccountsDto()));
		
		ResponseEntity<LoansDto> loansDtoResponseEntity=loansFeignClient.fetchLoan(coorelationId,mobileNumber);
		if(null!=loansDtoResponseEntity) {
			customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
		}
		
		
		ResponseEntity<CardsDto> cardsDtoResponseEntity=cardsFeignClient.fetchCardDetails(coorelationId,mobileNumber);
		if(null!=cardsDtoResponseEntity) {
			customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
		}
		return customerDetailsDto;
	}

	

}
