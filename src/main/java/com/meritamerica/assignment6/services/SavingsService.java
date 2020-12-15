package com.meritamerica.assignment6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.repositories.SavingsAccountRepository;

//**This class is specifically for the tasks of adding/searching the databases**

@Service
public class SavingsService {

	@Autowired // creates variables for this instance
	private SavingsAccountRepository repository;

	// add service
	public SavingsAccount addSavingsAccount(SavingsAccount account) {
		repository.save(account);
		return account;
	}

	// find by one account by id service
	public SavingsAccount findSavingsById(int id) {
		return repository.findById(id).orElse(null);
	}

	// return list of all accounts service
	public List<SavingsAccount> findAllAccounts() {
		return repository.findAll();
	}

}
