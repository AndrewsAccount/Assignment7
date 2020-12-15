package com.meritamerica.assignment6.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;



@Entity(name = "CheckingAccount")   //needed to create tables/repositories
@Table(name = "CheckingAccount") 
public class CheckingAccount extends BankAccount	 {  
	//inherits balance, dateOpened & getters/setters


	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero")
	@DecimalMax(value = "1", inclusive = false, message = "interest rate must be less than one")
	double interestRate = .0001;   //specific to checking accounts
	
	//default constructor
	public CheckingAccount() {
		super();
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
}