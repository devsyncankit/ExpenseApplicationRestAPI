package com.expenseTracker.expenseApp.expenditure.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expenseTracker.expenseApp.expenditure.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Page<Expense> findExpenseByCategory(String category,Pageable page);
	Page<Expense> findByUserId(Long userId, Pageable page);
	//Optional<Expense> findByUserIdAndId(Long userId , Long expenseId );
}
