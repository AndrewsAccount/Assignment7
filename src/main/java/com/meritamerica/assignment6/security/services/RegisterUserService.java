package com.meritamerica.assignment6.security.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.security.JwtUtil;
import com.meritamerica.assignment6.security.models.ERole;
import com.meritamerica.assignment6.security.models.Role;
import com.meritamerica.assignment6.security.models.SignupRequest;
import com.meritamerica.assignment6.security.models.User;
import com.meritamerica.assignment6.security.repositories.RoleRepository;
import com.meritamerica.assignment6.security.repositories.UserRepository;

@Service
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MyUserService myUserService;
	@Autowired
	private JwtUtil jwtUtil;
	
	public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body("Error: Username unavailable");
		}
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.AccountHolder).orElseThrow(() -> new RuntimeException("Error: Role not found."));
			
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin": Role adminRole = roleRepository.findByName(ERole.admin).
							  orElseThrow(() -> new RuntimeException("Error: Role not found."));
				
							  roles.add(adminRole);				
				break;
				
				case "AccountHolder":
						Role userRole = roleRepository.findByName(ERole.AccountHolder).
							  orElseThrow(() -> new RuntimeException("Error: Role not found."));
				
				}
			});
		}
		
		user.setActive(signUpRequest.isActive());
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok("User regisitered successfully.");
		}
}
