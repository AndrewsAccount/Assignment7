package com.meritamerica.assignment6.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment6.models.MyUserDetails;
import com.meritamerica.assignment6.models.User;
import com.meritamerica.assignment6.repositories.UserRepository;

@Service 
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(userName); // search user repo for this user
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		
		return user.map(MyUserDetails::new).get();
	}
	

}
