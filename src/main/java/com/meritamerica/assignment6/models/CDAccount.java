package com.meritamerica.assignment6.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("CDAccount") 
public class CDAccount extends BankAccount	 {   //inherits balance, dateOpened & getters/setters
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	//interest rate must be a positive number less than 1
	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero")
	@DecimalMax(value = "1.0", inclusive = false, message = "interest rate must be less than one")
	double interestRate;
	
	@NotNull // validation for int
	int term;
	
	// map instance of CDOffering to list of holder's cd accounts
	@ManyToOne   // each specific cd account derives from a list of many offerings (many-to-one)
	@JoinColumn(name = "offering_id")
	private CDOffering offering;
	
	public CDAccount() {  // all persistent classes must have default constructor for Hibernate to instantiate
		super();
	}
	
	// ---getters & setters---

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		return offering;
	}	
	public void setCDOffering(CDOffering offering) {
		this.offering = offering;
	}
}
