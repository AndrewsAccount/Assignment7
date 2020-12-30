package com.meritamerica.assignment6.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.exceptions.OfferingNotFoundException;
import com.meritamerica.assignment6.models.CDOffering;
import com.meritamerica.assignment6.repositories.CDOfferingRepository;

//**This class is specifically for the tasks of adding/searching the CD Offering Database**

@Service 
public class CDOfferingService {

	@Autowired
	CDOfferingRepository repository;   // constructor needed to update/search database
	
	// add offering to database
	public CDOffering addOffering(CDOffering offering) {
		repository.save(offering);
		return offering;
	}
	
	// find all cd offerings
	public List<CDOffering> findAll() {
		return repository.findAll();
	}
	
	// find offering by specific id
	public CDOffering findById(int id) throws OfferingNotFoundException {
		if(!(repository.existsById(id))) {
			throw new OfferingNotFoundException("Account not found");
		}	
		return repository.findById(id).orElse(null);
	}
}
