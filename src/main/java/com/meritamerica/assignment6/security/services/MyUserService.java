package com.meritamerica.assignment6.security.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.repositories.AccountHolderRepository;
import com.meritamerica.assignment6.repositories.CDAccountRepository;
import com.meritamerica.assignment6.repositories.CheckingAccountRepository;
import com.meritamerica.assignment6.repositories.SavingsAccountRepository;
import com.meritamerica.assignment6.security.JwtUtil;
import com.meritamerica.assignment6.security.models.MyUserDetails;
import com.meritamerica.assignment6.security.models.User;
import com.meritamerica.assignment6.security.repositories.UserRepository;

@Service
public class MyUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountHolderRepository accountHolderRepository;
	@Autowired
	private CheckingAccountRepository checkingAccountRepository;
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	@Autowired
	private CDAccountRepository cdAccountRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) {
		
		// search repo for user
		Optional<User> user = userRepository.findByUserName(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
		
		return user.map(MyUserDetails::new).get();
	}

	public AccountHolder getMyAccountInfo(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		AccountHolder accountHolder = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		if (username != null) {
			User user = this.userRepository.findByUserName(username).orElseThrow(null);
			accountHolder = user.getAccountHolder();
			accountHolder = accountHolderRepository.findById(user.getId()).orElseThrow(null);
		}
		
		return accountHolder;
	}
	
	public List<CheckingAccount> getMyCheckingAccounts(HttpServletRequest request) {
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getCheckingAccounts();
	}
	
	public CheckingAccount postMyCheckingAccount(HttpServletRequest request, CheckingAccount checkingAccount)
											throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);
	
		accountHolder.setCheckingAccounts(Arrays.asList(checkingAccount));
		checkingAccount.setAccountHolder(accountHolder);
		checkingAccountRepository.save(checkingAccount);
		
		return checkingAccount;
	}
	
	public List<SavingsAccount> getMySavingsAccounts(HttpServletRequest request) {
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getSavingsAccounts();
	}
	
	public SavingsAccount postMySavingsAccount(HttpServletRequest request, SavingsAccount savingsAccount)
											throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);
		
		accountHolder.setSavingsAccounts(Arrays.asList(savingsAccount));
		savingsAccount.setAccountHolder(accountHolder);
		savingsAccountRepository.save(savingsAccount);
		
		return savingsAccount;
	}
	
	public List <CDAccount> getMyCdAccounts(HttpServletRequest request) {
		
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getcdAccounts();
	}
	
	public CDAccount postMyCdSavingsAccount(HttpServletRequest request, CDAccount cdAccount)
			throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);

		accountHolder.setcdAccounts(Arrays.asList(cdAccount));
		cdAccount.setAccountHolder(accountHolder);
		cdAccountRepository.save(cdAccount);

		return cdAccount;
	}	
}
