package com.meritamerica.assignment6.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.repositories.AccountHolderRepository;

//**This class is specifically for the tasks of adding/searching the databases**

@Service
public class AccountHolderService {

	@Autowired // creates variables for this instance
	private AccountHolderRepository repository;

	// add service
	public AccountHolder addAccountHolder(AccountHolder account) {
		repository.save(account);
		return account;
	}

	// find by one account by id service
	public AccountHolder findById(int id) {
		return repository.findById(id).orElse(null);
	}

	// return list of all accounts service
	public List<AccountHolder> findAllAccountHolders(){
		return repository.findAll();
	}
}
