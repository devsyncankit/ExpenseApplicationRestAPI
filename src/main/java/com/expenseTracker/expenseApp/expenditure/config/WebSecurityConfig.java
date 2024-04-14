package com.expenseTracker.expenseApp.expenditure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.expenseTracker.expenseApp.expenditure.security.CustomUserDetailService;
import com.expenseTracker.expenseApp.expenditure.util.JwtRequestFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter() {
		return new JwtRequestFilter();
	}
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login", "/register").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.httpBasic();
	}
	 
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.userDetailsService(customUserDetailService);
		 
	}
	 
	 @Bean
	public  PasswordEncoder passwordEncoder() {
		 
		 return new BCryptPasswordEncoder();
		 
	 }
	
	 @Bean
	 @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		 
		 return super.authenticationManagerBean();
		 
	 }

}
