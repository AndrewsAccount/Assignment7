package com.meritamerica.assignment6.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table 
public class CDOffering {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cd_Offering")
	Integer id;
	@Min(value = 1, message = "Term must be at least 1 year")
	int term;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero")
	@DecimalMax(value = "1.0", inclusive = false, message = "interest rate must be less than one")
	double interestRate;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cDOffering", fetch = FetchType.LAZY)
	private List <CDAccount> cdAccounts;
	
	
	// default constructor
	CDOffering(){
		this.term = term;
		this.interestRate = interestRate;
	}
	
	//--- getters/setters
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
	
	@JsonManagedReference(value="cdAccount")
    public List<CDAccount> getcDAccounts() {
        return cdAccounts;
    }
	
	public void setCDAccounts(List<CDAccount> accounts) {
		this.cdAccounts = new ArrayList<CDAccount>(cdAccounts);
	}

}
