package com.expenseTracker.expenseApp.expenditure.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.expenseApp.expenditure.entity.AuthModel;
import com.expenseTracker.expenseApp.expenditure.entity.JwtResponse;
import com.expenseTracker.expenseApp.expenditure.entity.User;
import com.expenseTracker.expenseApp.expenditure.entity.UserModel;
import com.expenseTracker.expenseApp.expenditure.security.CustomUserDetailService;
import com.expenseTracker.expenseApp.expenditure.service.UserService;
import com.expenseTracker.expenseApp.expenditure.util.JwtTokenUtil;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception{
		
	
		authenticate(authModel.getEmail(), authModel.getPassword());
	
		final UserDetails userdetails = customUserDetailService.loadUserByUsername(authModel.getEmail());
		
		final String jwtToken = jwtTokenUtil.generateToken(userdetails);
		
		return new ResponseEntity<JwtResponse>(new JwtResponse(jwtToken) , HttpStatus.OK);
	}
	
	private void authenticate(String email, String password) throws Exception {
		try {
			 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("user disabled");
		}catch (BadCredentialsException e){
			throw new Exception("Bad Credentials");
		}
		
	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
		
		return new ResponseEntity<User>(userService.createUser(user),HttpStatus.CREATED);
		
		
	}

}
