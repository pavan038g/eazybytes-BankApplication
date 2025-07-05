package com.eazybytes.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.accounts.Exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.Exception.ResourceNotFoundException;
import com.eazybytes.accounts.Mapper.AccountMapper;
import com.eazybytes.accounts.Mapper.CustomerMapper;
import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
	
	private AccountsRepository accountRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer=CustomerMapper.mapToCustomer(new Customer(), customerDto);
		
		Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalCustomer.isPresent()) {
			
			throw new CustomerAlreadyExistException("customer already exist with the given mobilenumber"+customerDto.getMobileNumber());
		}
		Customer savedcustomer=customerRepository.save(customer);
		accountRepository.save(createNewAccount(savedcustomer));
		
	}
	
	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomNumber = 10000000000L+new Random().nextInt(900000000);
		newAccount.setAccountNumber(randomNumber);
		newAccount.setAccountType(AccountConstants.SAVINGS );
		newAccount.setBranchAddress(AccountConstants.ADDRESS);
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));
		Accounts accounts=accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()->new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
		CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		
		customerDto.setAccountDto(AccountMapper.mapToAccountDto(accounts, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		
		AccountsDto accountsDto=customerDto.getAccountDto();
		if(accountsDto!=null) {
			Accounts accounts=accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
					()-> new ResourceNotFoundException("Account", "AccountNuumber",accountsDto.getAccountNumber().toString() ));
			
			AccountMapper.mapToAccount(accounts, accountsDto);
			accounts=accountRepository.save(accounts);
			
			Long customerId=accounts.getCustomerId();
			Customer customer=customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer", "CustomerId",customerId.toString() ));
			
			CustomerMapper.mapToCustomer(customer, customerDto);
			
			customerRepository.save(customer);
			isUpdated=true;
		}
		
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber.toString()));
		accountRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

}
