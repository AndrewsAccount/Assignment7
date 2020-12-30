package com.meritamerica.assignment6.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;



@Entity   //needed to create tables/repositories
@DiscriminatorValue("Checking")
public class CheckingAccount extends BankAccount	 {  
	//inherits balance, dateOpened & getters/setters as fields and methods


	// insures interest rate is a positive number that is less than 1
	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero")
	@DecimalMax(value = "1", inclusive = false, message = "interest rate must be less than one")
	double interestRate = .0001;   //specific to checking accounts
	
	// all persistent classes in must have default constructor for Hibernate to instantiate
	public CheckingAccount() {	
		super();
	}
	
	// all other methods inherited by super class
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
}