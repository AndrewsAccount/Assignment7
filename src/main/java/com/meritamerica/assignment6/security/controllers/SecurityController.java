package com.meritamerica.assignment6.security.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meritamerica.assignment6.security.JwtUtil;
import com.meritamerica.assignment6.security.models.AuthenticationRequest;
import com.meritamerica.assignment6.security.models.AuthenticationResponse;
import com.meritamerica.assignment6.security.models.SignupRequest;
import com.meritamerica.assignment6.security.services.MyUserService;
import com.meritamerica.assignment6.security.services.RegisterUserService;

@Controller
public class SecurityController {

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
}

