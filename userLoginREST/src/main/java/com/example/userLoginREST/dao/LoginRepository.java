package com.example.userLoginREST.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userLoginREST.entity.User;

public interface LoginRepository extends JpaRepository<User, Integer> {

}