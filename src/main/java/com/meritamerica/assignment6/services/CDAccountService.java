package com.meritamerica.assignment6.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.repositories.CDAccountRepository;

//**This class is specifically for the tasks of adding/searching the CD Account database**

@Service
public class CDAccountService {

	@Autowired // creates variables for this instance
	private CDAccountRepository repository;         // constructor needed to update/search databases
	@Autowired 
	private AccountHolderService holderService;		// constructor needed to find holder by id

	// add cd account to database
	public CDAccount addAccount(CDAccount account, Integer id) throws AccountNotFoundException {
		AccountHolder holder = holderService.findById(id);   // use id given to locate holder
		holder.setcdAccounts(Arrays.asList(account));  		 // use Arrays class to convert array of account to List 
		account.setAccountHolder(holder);  					 // set list of cd accounts to belong to holder
		repository.save(account);							 // add given account to that holder's list of cd accounts
		return account;
	}

	// show all of this holder's cd accounts
	public List<CDAccount> findAccounts(Integer id) throws AccountNotFoundException {
		return holderService.findById(id).getcdAccounts();
	}
}