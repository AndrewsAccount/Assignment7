package com.meritamerica.assignment6.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.security.JwtUtil;
import com.meritamerica.assignment6.security.models.AuthenticationRequest;
import com.meritamerica.assignment6.security.models.AuthenticationResponse;
import com.meritamerica.assignment6.security.models.SignupRequest;
import com.meritamerica.assignment6.security.services.MyUserService;
import com.meritamerica.assignment6.security.services.RegisterUserService;

@RestController
public class MyUserController {
	Logger log = LoggerFactory.getLogger(this.getClass());	

	@Autowired
	private AuthenticationManager authenticationManager;	
	@Autowired
	private MyUserService myUserService;
	@Autowired
	private RegisterUserService registerUserService;	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/authenticate/createUser")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return registerUserService.registerUser(signUpRequest);
	}	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
													  throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = myUserService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me")
	public AccountHolder getMyAccountInfo(HttpServletRequest request) {
		return myUserService.getMyAccountInfo(request);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/CheckingAccount")
	public CheckingAccount postMyCheckingAccont(HttpServletRequest request, @Valid @RequestBody CheckingAccount checkingAccount)
												throws ExceedsCombinedBalanceLimitException {
		return myUserService.postMyCheckingAccount(request, checkingAccount);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/CheckingAccount")
	public List<CheckingAccount> getMyCheckingAccounts(HttpServletRequest request) {
		return myUserService.getMyCheckingAccounts(request);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/CheckingAccount")
	public SavingsAccount postMySavingsAccount(HttpServletRequest request, @Valid @RequestBody SavingsAccount savingsAccount)
												throws ExceedsCombinedBalanceLimitException {
		return myUserService.postMySavingsAccount(request, savingsAccount);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/CheckingAccount")
	public List<SavingsAccount> getMySavingsAccount(HttpServletRequest request) {
		return myUserService.getMySavingsAccounts(request);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/CheckingAccount")
	public CDAccount postMyCDAccount(HttpServletRequest request, @Valid @RequestBody CDAccount cdAccount)
												throws ExceedsCombinedBalanceLimitException {
		return myUserService.postMyCdSavingsAccount(request, cdAccount);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/CheckingAccount")
	public List<CDAccount> getMyCdAccounts(HttpServletRequest request) {
		return myUserService.getMyCdAccounts(request);
	}
}
