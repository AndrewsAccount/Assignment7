package com.meritamerica.assignment6.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.models.CDOffering;
import com.meritamerica.assignment6.repositories.CDOfferingRepository;

@Service 
public class CDOfferingService {

	CDOfferingRepository repository;
	
	// add offering
	public CDOffering addOffering(CDOffering offering) {
		repository.save(offering);
		return offering;
	}
	
	// find all cd offerings
	public List<CDOffering> findAll() {
		return repository.findAll();
	}
	
	// find offering by specific identifier, generated for each offering in Model Class;
	public CDOffering findById(int id) {
		return repository.findById(id).orElse(null);
	}
}
