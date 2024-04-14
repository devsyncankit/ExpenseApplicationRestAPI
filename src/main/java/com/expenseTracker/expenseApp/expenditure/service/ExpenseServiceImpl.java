package com.expenseTracker.expenseApp.expenditure.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.expenseTracker.expenseApp.expenditure.entity.Expense;
import com.expenseTracker.expenseApp.expenditure.exceptions.ResourceNotFoundException;
import com.expenseTracker.expenseApp.expenditure.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired UserService userService;
	
	@Override
	public Page<Expense> getAllExpenses(Pageable page) {
		
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
		
	}

	@Override
	public Expense getExpenseById(Long id) {
		
	Optional<Expense> expense =	expenseRepo.findById(id);
	if(expense.isPresent()) {
		return expense.get();
	}
	throw new ResourceNotFoundException("Expense is not found");
		
		
	}

	@Override
	public void deleteExpenseById(Long id) {
		
		Expense expense = getExpenseById(id);
		
		expenseRepo.delete(expense);
		
		
	}

	@Override
	public Expense saveExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Expense updateExpenseDetails( Long id, Expense expense) {
		Expense existingExpense = getExpenseById(id);
		existingExpense.setName(expense.getName() != null ? expense.getName(): existingExpense.getName());
		existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		return expenseRepo.save(existingExpense);
		
	}

	@Override
	public List<Expense> readExpenseByCategory(String category, Pageable page) {
	
		return expenseRepo.findExpenseByCategory(category, page).toList();
	}

	

}
