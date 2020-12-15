package com.meritamerica.assignment6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.exceptions.AccountNotFoundException;
import com.meritamerica.assignment6.models.AccountHolderContactDetails;
import com.meritamerica.assignment6.repositories.DetailsRepository;

//**This class is specifically for the tasks of adding/searching the databases**

@Service
public class DetailsService {

	@Autowired // creates variables for this instance
	private DetailsRepository repository;

	// add service
	public AccountHolderContactDetails addDetails(AccountHolderContactDetails details) {
		repository.save(details);
		return details;
	}

	// find by one account by id service
	public AccountHolderContactDetails findById(int id) throws AccountNotFoundException {
		if(!(repository.existsById(id))) {
			throw new AccountNotFoundException("Account not found");
		}
		return repository.findById(id).orElse(null);
	}

	// return list of all accounts service
	public List<AccountHolderContactDetails> findAllAccounts() {
		return repository.findAll();
	}
}