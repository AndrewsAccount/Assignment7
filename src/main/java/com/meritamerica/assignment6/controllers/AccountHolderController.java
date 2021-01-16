package com.meritamerica.assignment6.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.exceptions.InterestRateOutOfBoundsException;
import com.meritamerica.assignment6.exceptions.NegativeBalanceException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.AccountHolderContactDetails;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.security.JwtUtil;
import com.meritamerica.assignment6.security.models.SignupRequest;
import com.meritamerica.assignment6.services.AccountHolderService;
import com.meritamerica.assignment6.services.CDAccountService;
import com.meritamerica.assignment6.services.CheckingService;
import com.meritamerica.assignment6.services.DetailsService;
import com.meritamerica.assignment6.services.SavingsService;

// MAIN CONTROLLER for - Account Holders, Contact Details & Account types: Checking, Savings, CD  
// calls services of holder, details, & each account types to control CRUD methods
@RestController
public class AccountHolderController {
	Logger logger = LoggerFactory.getLogger(AccountHolderController.class);


	// **constructors for services controlled **
	@Autowired  						  // finds anything needed to be injected in this constructor and injects them for you
	private AccountHolderService holderService;  
	@Autowired
	private DetailsService detailsService;
	@Autowired
	private CheckingService checkingService;
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private CDAccountService cdAccountService;

	
	// --- CRUD METHODS ---

	//--find an account holder by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}")
	public AccountHolder findById(@PathVariable Integer id) throws AccountNotFoundException {
		return holderService.findById(id);
	}
	
	//--add account holder
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders")
	public AccountHolder addAccountHolder(@Valid @RequestBody AccountHolder account) {
		holderService.addAccountHolder(account);
		return account;
	}

	//--find all account holders
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders")
	public List<AccountHolder> findAllAccountHolders() {
		return holderService.findAllAccountHolders();
	}
	
	//--add contact details
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/Details")
	public AccountHolderContactDetails addDetails(@Valid @PathVariable Integer id,
			@RequestBody AccountHolderContactDetails details) {
		return detailsService.addDetails(details);
	}

	//--find contact details
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/Details")
	public Object getDetails(Integer id) throws AccountNotFoundException {
		return detailsService.findById(id);
	}
	
	//--add checking account
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount addCheckingAccount(@Valid @RequestBody CheckingAccount account, @PathVariable Integer id)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((AccountHolder) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return checkingService.addCheckingAccount(account, id);
	}

	//--show list of holder's checking accounts using the holder's id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/CheckingAccounts")
	public List<CheckingAccount> findCheckingById(@PathVariable Integer id) throws AccountNotFoundException {
		return checkingService.findAccounts(id);
	}

	
	//--add savings account
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public SavingsAccount addSavingsAccount(@Valid @PathVariable Integer id, @RequestBody SavingsAccount account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {		
	
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((AccountHolder) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return savingsService.addSavingsAccount(account, id);
	}

	//--find savings account by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/SavingsAccounts")
	public List<SavingsAccount> findSavingsById(@PathVariable Integer id) throws AccountNotFoundException {
		return savingsService.findAccounts(id);
	}

	//--add cd account to holder's list of cd accounts
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@Valid @PathVariable Integer id, @RequestBody CDAccount account) 
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException, InterestRateOutOfBoundsException {		
		
		// balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((AccountHolder) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if (account.getInterestRate() <= 0 || account.getInterestRate() >=1) {
			throw new InterestRateOutOfBoundsException("Interest Rate must ;greater than zero & less than one");
		}
		return cdAccountService.addAccount(account, id);
	}

	//--show list of cd accounts for holder
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/CDAccounts")
	public List<CDAccount> getCDAccounts(@PathVariable Integer id) throws AccountNotFoundException {
		return cdAccountService.findAccounts(id);
	}
}