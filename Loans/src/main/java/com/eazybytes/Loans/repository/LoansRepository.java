package com.eazybytes.Loans.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.Loans.entity.Loans;

public interface LoansRepository extends JpaRepository<Loans, Long>{

	Optional<Loans> findByLoanNumber(String accountNumber);

	Optional<Loans> findByMobileNumber(String mobileNumber);

}
