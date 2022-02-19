package com.services.Interfaces;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.entities.User;

public interface IUserService {
	String addUser(User user);
	String updateUser(User user);
	String deleteUser(User user);
	User retrieveUserByUsername(String username);
	User retrieveUserByEmail(String email);
	List<User> retrieveAllUsers();
	UserDetails loadUserByUsername(String username);	
}
