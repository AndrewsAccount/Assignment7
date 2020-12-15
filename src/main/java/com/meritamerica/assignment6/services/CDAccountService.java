package com.meritamerica.assignment6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.repositories.CDAccountRepository;


//**This class is specifically for the tasks of adding/searching the databases**

@Service
public class CDAccountService {

	@Autowired // creates variables for this instance
	private CDAccountRepository repository;

	// add service
	public CDAccount addCDAccount(CDAccount account) {
		repository.save(account);
		return account;
	}

	// find by one account by id service
	public CDAccount findById(int id) throws AccountNotFoundException {
		if(!(repository.existsById(id))) {
			throw new AccountNotFoundException("Account not found");
		}
		return repository.findById(id).orElse(null);
	}

	// return list of all accounts service
	public List<CDAccount> findAllAccounts() {
		return repository.findAll();
	}
}