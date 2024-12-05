package com.example.userLoginREST.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userLoginREST.dao.LoginRepository;
import com.example.userLoginREST.dto.LoginRequestDTO;
import com.example.userLoginREST.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private LoginRepository loginRepo;
	
	@Override
	public String loginUser(LoginRequestDTO loginDTO) {
		return null;
	}

}
