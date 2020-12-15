package com.meritamerica.assignment6.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.exceptions.NegativeBalanceException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.AccountHolderContactDetails;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.repositories.AccountHolderRepository;
import com.meritamerica.assignment6.services.AccountHolderService;
import com.meritamerica.assignment6.services.CDAccountService;
import com.meritamerica.assignment6.services.CheckingService;
import com.meritamerica.assignment6.services.DetailsService;
import com.meritamerica.assignment6.services.SavingsService;

//** MAIN CONTROLLER FOR ALL ACCOUNTS**\\
@RestController
public class AccountHolderController {

	// services controlled here
	@Autowired // autowire imports the constructors from each service
	AccountHolderService accountHolderService;
	@Autowired
	CDAccountService cdAccountService;
	@Autowired
	CheckingService checkingService;
	@Autowired
	DetailsService detailsService;
	@Autowired
	SavingsService savingsService;

	// ---CRUD methods----

	/*
	 * How to know which account holder is being referred to in each
	 * saving/checking/cd account calls In Services, how to getById: Optional<> or
	 * .orElse()??
	 * 
	 */
	// add account holder
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders")
	public AccountHolder addAccountHolder(@Valid @RequestBody AccountHolder account) {
		accountHolderService.addAccountHolder(account);
		return account;
	}

	// find all account holders
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders")
	public List<AccountHolder> findAllAccountHolders() {
		return accountHolderService.findAllAccountHolders();
	}

	// find an account holder by id
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}")
	public AccountHolder findById(@PathVariable int id) throws AccountNotFoundException {
		return accountHolderService.findById(id);
	}

	
	// add contact details
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/Details")
	public AccountHolderContactDetails addDetails(@PathVariable int id,
			@RequestBody AccountHolderContactDetails details) {
		return detailsService.addDetails(details);
	}

	// find contact details
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/Details")
	public Object getDetails(int id) throws AccountNotFoundException {
		return detailsService.findById(id);
	}

	// add checking account
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccount")
	public CheckingAccount addCheckingAccount(@PathVariable int id, @RequestBody CheckingAccount account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		
		// balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((AccountHolder) findById(id)).getCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return checkingService.addCheckingAccount(account);
	}

	// find checking by id
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/CheckingAccount")
	public CheckingAccount findCheckingById(@PathVariable int id) {
		// return checkingAccountService.findById(accountHolderService.findById(id));
		return null;
	}

	// add savings account
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccount")
	public SavingsAccount addSavingsAccount(@PathVariable int id, @RequestBody SavingsAccount account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		
		// balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((AccountHolder) findById(id)).getCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return savingsService.addSavingsAccount(account);
	}

	// find savings account by id
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/SavingsAccount")
	public Object findSavingsById(@PathVariable int id) throws AccountNotFoundException {
		return savingsService.findSavingsById(id);
	}

	// add cd account
	@PostMapping(value = "/AccountHolders/{id}/CDAccount")
	@ResponseStatus(HttpStatus.CREATED)
	public Object addCDAccount(@PathVariable int id, @RequestBody CDAccount account)
			throws NegativeBalanceException {
		
		// balance may not be negative
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		return cdAccountService.addCDAccount(account);
	}

	//find cd account by id
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/CDAccount")
	public Object getCDAccountById(@PathVariable int id) throws AccountNotFoundException {
		return cdAccountService.findById(id);
	}

}
