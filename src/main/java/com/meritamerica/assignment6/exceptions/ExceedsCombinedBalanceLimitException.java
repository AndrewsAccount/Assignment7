package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//if combined balance of all accounts exceeds alloted amount (currently $250,000 USD)

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceedsCombinedBalanceLimitException extends Exception {

	public ExceedsCombinedBalanceLimitException(String errorMessage){
		super(errorMessage);
	}

}
