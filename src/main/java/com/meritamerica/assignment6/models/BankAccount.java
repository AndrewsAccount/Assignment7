package com.meritamerica.assignment6.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;

//Directions hinted at using @MappedSuperClass, however, using @Entity/@inheritance allows each sublass to be a table
@Entity(name = "BankAccount")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BankAccount {
	
	// ensure positive value
	@Min(value = 0, message = "Cannot have negative balance.")
	double balance;
	// uses local date - will only be valuable if info is input same day, and also locally
	LocalDate dateOpened;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "accountHolder_id")
	private AccountHolder accountHolder;
	
	// default constructor
	public BankAccount(){		
		this.dateOpened = LocalDate.now();
	}
	

	//----------Getters/Setters
	
	@JsonBackReference  // gets rid of infinite recursion
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDate getDateOpened() {
		return dateOpened;
	}
	
	public void setDateOpened(LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}

}
