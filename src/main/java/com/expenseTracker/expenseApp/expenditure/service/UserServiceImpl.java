package com.expenseTracker.expenseApp.expenditure.service;

import java.beans.Encoder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expenseTracker.expenseApp.expenditure.entity.User;
import com.expenseTracker.expenseApp.expenditure.entity.UserModel;
import com.expenseTracker.expenseApp.expenditure.exceptions.ItemAlreadyExistsException;
import com.expenseTracker.expenseApp.expenditure.repository.UserRepository;
import com.expenseTracker.expenseApp.expenditure.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder bPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(UserModel user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			
			throw new ItemAlreadyExistsException("User is already registered");
		}
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bPasswordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public User readUser(Long id) {
	
	return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"+id));
	}

	@Override
	public User updateUserById(UserModel user, Long id) {
		
		User existingUser = readUser(id);
		existingUser.setName(user.getName() !=null ? user.getName(): existingUser.getName());
		existingUser.setEmail(user.getEmail() !=null? user.getEmail(): existingUser.getEmail());
		existingUser.setPassword(user.getPassword() !=null? bPasswordEncoder.encode(user.getPassword()): existingUser.getPassword());
		existingUser.setAge(user.getAge() !=null? user.getAge(): existingUser.getAge());
		
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		User exixtingUser = readUser(id);
		userRepository.delete(exixtingUser);
		
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user details not found"+ email));
	}

}
