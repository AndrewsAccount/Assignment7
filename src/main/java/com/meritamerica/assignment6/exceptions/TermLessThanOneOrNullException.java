package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// all cd offering term lengths must exceed at least one year

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TermLessThanOneOrNullException extends Exception {

	public TermLessThanOneOrNullException(String errorMessage) {
		super(errorMessage);
	}
}
