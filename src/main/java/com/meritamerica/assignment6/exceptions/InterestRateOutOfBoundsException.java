package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// if interest rate is not within accepted range 

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InterestRateOutOfBoundsException extends Exception {

	public InterestRateOutOfBoundsException(String errorMessage) {
		super(errorMessage);
	}
}
