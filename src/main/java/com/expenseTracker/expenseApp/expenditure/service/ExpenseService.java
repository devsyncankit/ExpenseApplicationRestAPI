package com.expenseTracker.expenseApp.expenditure.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expenseTracker.expenseApp.expenditure.entity.Expense;


public interface ExpenseService {

	Page<Expense> getAllExpenses(Pageable Page);
	Expense getExpenseById(Long id);
	void deleteExpenseById(Long id);
	Expense saveExpenseDetails(Expense expense);
	Expense updateExpenseDetails(Long id , Expense expense);
	List<Expense> readExpenseByCategory(String category, Pageable page);
	
}
