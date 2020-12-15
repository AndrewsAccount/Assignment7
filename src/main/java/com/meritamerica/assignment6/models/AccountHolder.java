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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class AccountHolder {

	public AccountHolder() {
		
	}
	
	// Required details for each Account
	
	@NotNull(message = "First Name can not be null")
	@NotBlank(message = "First Name can not be blank")
	String firstName;
	
	@NotNull(message = "Middle Name can not be null")
	@NotBlank(message = "Middle Name can not be blank")
	String middleName;
	
	@NotNull(message = "Last Name can not be null")
	@NotBlank(message = "Last Name can not be blank")
	String lastName;
	
	@NotNull(message = "Social Security Number Name can not be null")
	@NotBlank(message = "Social Security Number can not be blank")
	String ssn;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountHolder_id")
	Integer id;
	
	
	
	//----- table relationships and array lists will also help keep track of combined balances`
	
	// each accountHolder has 1 set of Details & Each set of Details belongs to only 1 AccountHolder
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private AccountHolderContactDetails contactDetails;

	// an AccountHolder may have numerous CheckingAccounts, but each CheckingAccount can only have 1 AccountHolder
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<CheckingAccount> checkingAccounts;
	
	// an AccountHolder may have numerous SavingsAccounts, but each SavingsAccount can only have 1 AccountHolder
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<SavingsAccount> savingsAccounts;
	
	// an AccountHolder may have numerous CDAccounts, but each particular CDAccount can only have 1 AccountHolder
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<CDAccount> cdAccounts;

	
	
	//------Getters/Setters for variables -------
	
	public String getFirstName() {
		return firstName;
	}

	public AccountHolder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public AccountHolder setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public AccountHolder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getSsn() {
		return ssn;
	}

	public AccountHolder setSsn(String ssn) {
		this.ssn = ssn;
		return this;
	}
	
	
	
	//---- Methods for getting contents of entire tables/lists  -----
	
	@JsonManagedReference   // used to get out of endless recursion 
	public List<CheckingAccount> getCheckingAccounts() {
		return checkingAccounts;
	}

	public void setCheckingAccounts(List<CheckingAccount> checkingAccounts) {
		this.checkingAccounts = new ArrayList<CheckingAccount>(checkingAccounts);
	}

	@JsonManagedReference
	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}

	public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
		this.savingsAccounts = new ArrayList<SavingsAccount>(savingsAccounts);
	}
	
	@JsonManagedReference
	public List<CDAccount> getcDAccounts() {
		return cdAccounts;
	}

	public void setcDAccounts(List<CDAccount> cDAccounts) {
		this.cdAccounts = new ArrayList<CDAccount>(cDAccounts);
	}
	
	
	
	// -- get/set id
	
	public Integer getId() {
		return id;
	}

	public AccountHolder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	
	
	//---- get combined balances 
	
	public double getCombinedCheckingBalance() {
		double combinedCheckingBalance = 0;
		if (checkingAccounts != null) {
			for (BankAccount checkAcct : checkingAccounts) {
				combinedCheckingBalance += checkAcct.getBalance();
			}
			return combinedCheckingBalance;
		}
		return 0;
	}
	
	public double getCombinedSavingsBalance() {
		double combinedSavingsBalance = 0;
		if (savingsAccounts != null) {
			for (BankAccount savAcct : savingsAccounts) {
				combinedSavingsBalance += savAcct.getBalance();
			}
			return combinedSavingsBalance;
		}
		return 0;
	}
	
	public double getCombinedCDBalance() {
		double combinedCDBalance = 0;
		if (cdAccounts != null) {
			for (BankAccount cdAcct : cdAccounts) {
				combinedCDBalance += cdAcct.getBalance();
			}
			return combinedCDBalance;
		}
		return 0;
	}
	
	public double getCombinedBalances() {
		return getCombinedSavingsBalance() + getCombinedCheckingBalance() + getCombinedCDBalance();
	}
	
}
