package com.example.userLoginREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	

	
	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public String home(){
	    return "Welcome to User Dashboard!"; 
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String homeAdmin() {
		return "Welcome to Admin Dashboard";
	}
	
}