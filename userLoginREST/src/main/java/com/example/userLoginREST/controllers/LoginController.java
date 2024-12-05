package com.example.userLoginREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userLoginREST.config.JwtUtils;
import com.example.userLoginREST.dao.UserRepository;
import com.example.userLoginREST.dto.LoginRequestDTO;
import com.example.userLoginREST.dto.LoginResponseDTO;
import com.example.userLoginREST.dto.UserRegisterDTO;
import com.example.userLoginREST.entity.User;
import com.example.userLoginREST.service.LoginService;
import com.example.userLoginREST.serviceImpl.UserDetailsServiceImpl;

@RestController
@RequestMapping("user")
public class LoginController {

	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	
	
	@PostMapping("register")
	public ResponseEntity<?> signup(@RequestBody UserRegisterDTO register){
		if(register!=null) {
			User user = User.builder()
			.email(register.getEmail())
			.name(register.getName())
			.password(passwordEncoder.encode(register.getPassword()))
			.build();
			user = userDetailsServiceImpl.saveUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO login){
		if(login!=null) {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
		    if(authentication.isAuthenticated()){
		    	LoginResponseDTO token = LoginResponseDTO
		    		   .builder()
		               .token(jwtUtils.GenerateToken(login.getEmail()))
		               .build();
		    	return new ResponseEntity<LoginResponseDTO>(token, HttpStatus.OK);
		    } else {
		        throw new UsernameNotFoundException("invalid user request..!!");
		    }
		}		
		return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
	}
}