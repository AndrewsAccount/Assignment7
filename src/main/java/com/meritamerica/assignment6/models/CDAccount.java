package com.meritamerica.assignment6.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CD Account")
public class CDAccount extends BankAccount	 {  
	//inherits balance, dateOpened & getters/setters
	
	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero")
	@DecimalMax(value = "1.0", inclusive = false, message = "interest rate must be less than one")
	double interestRate;
	int term;
	
	@ManyToOne 
	@JoinColumn(name = "cd_Offering")
	private CDOffering cdOffering;
	
	public CDAccount() {
		super();
		this.cdOffering = getOfferingFromCDAccount();
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	@JsonBackReference(value = "cdAccount")
	public CDOffering getcDOffering() {
		return cdOffering;
	}
	
	public CDOffering getOfferingFromCDAccount() {
		CDOffering offering = new CDOffering();
		offering.setInterestRate(this.interestRate);
		offering.setTerm(this.term);
		return offering;
	}

}
