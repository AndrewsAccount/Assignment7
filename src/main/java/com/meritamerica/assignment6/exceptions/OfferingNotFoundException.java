package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// if id used to find an offering does not exist in the datatbase

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferingNotFoundException extends Exception {
	
	public OfferingNotFoundException(String message) {
		super(message);
	}
}
