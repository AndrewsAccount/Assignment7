package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// cannot add a balance with a negative or zero balance

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegativeBalanceException extends Exception {

	public NegativeBalanceException(String errorMessage){
		super(errorMessage);
	}


}
