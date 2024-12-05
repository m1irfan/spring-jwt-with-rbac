package com.example.userLoginREST.service;

import com.example.userLoginREST.dto.LoginRequestDTO;

public interface LoginService {
	
	public String loginUser(LoginRequestDTO loginDTO);

}