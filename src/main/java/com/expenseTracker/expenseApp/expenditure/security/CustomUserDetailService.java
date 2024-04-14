package com.expenseTracker.expenseApp.expenditure.security;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




import com.expenseTracker.expenseApp.expenditure.entity.User;
import com.expenseTracker.expenseApp.expenditure.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User existingUser = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user details not found"+ email));
		
		// Assuming 'getRole()' returns a single role as String
	    String userRole = existingUser.getName(); // Retrieve user role from the User entity

	    // If user has no role, assign a default role
	    if (userRole == null) {
	        userRole = "ROLE_USER"; // Assign a default role (you can adjust as needed)
	    }
		
		return new org.springframework.security.core.userdetails.User(existingUser.getEmail(), existingUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority(userRole)));
		//return new org.springframework.security.core.userdetails.User(existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
	}

}
