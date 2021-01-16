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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.meritamerica.assignment6.security.models.User;

//** This class has lists of accounts of each type and basic information on each account holder

@Entity
@Table(name = "AccountHolder")				// sets the order in which the fields of account holder object are returned in Json
@JsonPropertyOrder({"id", "firstName", "middleName", "lastName", "ssn", 
					"checkingAccounts", "savingsAccounts", "cdAccounts",
					"numberOfCheckingAccounts", "combinedCheckingBalance", 
					"numberOfSavingsAccounts", "combinedSavingsBalance", 
					"numberOfCdAccounts", "combinedCDBalance", 
					"getTotalCombinedBalances"})
public class AccountHolder {
	
	// Required details for each Account
	
	@Id //This will be the id used to reference each specific account holder
	@GeneratedValue(strategy = GenerationType.AUTO)  // Auto-generation of the ids
	@Column(name = "accountHolder_id") // this column is used to link to each of the account types for each account holder
	Integer id;
	
	@NotNull(message = "First Name can not be null")     // @notblank/@notnull to ensure required fields 
	@NotBlank(message = "First Name can not be blank")
	String firstName;
	
	// middle name not necessary
	String middleName;
	
	@NotNull(message = "Last Name can not be null")
	@NotBlank(message = "Last Name can not be blank")
	String lastName;
	
	@NotNull(message = "Social Security Number Name can not be null")
	@NotBlank(message = "Social Security Number can not be blank")
	String ssn;
	
		
	//----- table relationships and array lists ----
	
	// this maps each account holder to each of the following types of accounts
	// (will also help keep track of combined balances)
	// CascadeType.ALL - any change that happens to this(accountHolder) entity, must update the associated account types as well
	
	// each accountHolder has 1 set of Details & each set of Details belongs to only 1 AccountHolder (one-to-one)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_holders_contact_Details_id", referencedColumnName = "account_holders_contact_details_id")
	private AccountHolderContactDetails contactDetails;

	// an AccountHolder may have numerous CheckingAccounts, but each list of CheckingAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<CheckingAccount> checkingAccounts;
	
	// an AccountHolder may have numerous SavingsAccounts, but each list of SavingsAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<SavingsAccount> savingsAccounts;
	
	// an AccountHolder may have numerous CDAccounts, but each list of CDAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<CDAccount> cdAccounts;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "users_id")
	private User user;
	
	
	public AccountHolder() { // all persistent classes in must have default constructor for Hibernate to instantiate
	}
	
	//---Getters--- ** this is the information that will be returned upon requests**
	
	public String getFirstName() {
		return firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getSsn() {
		return ssn;
	}	
	public Integer getId() {
			return id;
	}
	
	// ---get/set account lists---
	
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
	public List<CDAccount> getcdAccounts() {
		return cdAccounts;
	}
	public void setcdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = new ArrayList<CDAccount>(cdAccounts);
	}
	
	
	
		
	//---- get combined balances & number of accounts---
	
	// get number of checking
	public int getNumberOfCheckingAccounts() {
		int total = 0;
		if (checkingAccounts != null) {
			for(CheckingAccount checkAcct : checkingAccounts) {
				total++;
			}
			return total;
		}
		return total;
	}
	
	// get combined checking
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
	
	// get number of savings 
	public int getNumberOfSavingsAccounts() {
		int total = 0;
		if (savingsAccounts != null) {
			for(SavingsAccount saveAcct : savingsAccounts) {
				total++;
			}
			return total;
		}
		return total;
	}
	
	// combined savings
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
	
	// number of cd accounts
	public int getNumberOfCdAccounts() {
		int total = 0;
		if (cdAccounts != null) {
			for(CDAccount cdAcct : cdAccounts) {
				total++;
			}
			return total;
		}
		return total;
	}
	
	// combined cd balance
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
	
	// total of all balances
	public double getTotalCombinedBalances() {
		return getCombinedSavingsBalance() + getCombinedCheckingBalance() + getCombinedCDBalance();
	}
}
