package com.meritamerica.assignment6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment6.services.CDOfferingService;

@RestController 
public class CDOfferingController {

	@Autowired
	CDOfferingService offeringService;
}
