package com.meritamerica.assignment6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.repositories.CheckingAccountRepository;

//**This class is specifically for the tasks of adding/searching the databases**

@Service
public class CheckingService {

	@Autowired // creates variables for this instance
	private CheckingAccountRepository repository;

	// add service
	public CheckingAccount addCheckingAccount(CheckingAccount account) {
		repository.save(account);
		return account;
	}

	// find by one account by id service
	public CheckingAccount findCheckingById(int id) {
		
		return repository.findById(id).orElse(null);
	}

	// return list of all accounts service
	public List<CheckingAccount> findAllAccounts() {
		return repository.findAll();
	}

}
