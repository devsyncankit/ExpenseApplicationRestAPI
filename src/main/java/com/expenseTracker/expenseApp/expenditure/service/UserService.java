package com.expenseTracker.expenseApp.expenditure.service;

import com.expenseTracker.expenseApp.expenditure.entity.User;
import com.expenseTracker.expenseApp.expenditure.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	
	User readUser(Long id);
	
	User updateUserById(UserModel user, Long id);
	
	void deleteUser(Long id);
	
	User getLoggedInUser();

}
