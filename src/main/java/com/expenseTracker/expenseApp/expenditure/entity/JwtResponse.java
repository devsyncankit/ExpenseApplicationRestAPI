package com.expenseTracker.expenseApp.expenditure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {


	public JwtResponse(String jwtToken) {
		// TODO Auto-generated constructor stub
	}

	private  String jwtToken = "";
}
