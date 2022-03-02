package com.services.Interfaces;

import java.util.List;


import com.entities.User;

public interface IUserService {
	String addCompany(User user);
	String addEmployee(User user);
	String update(User user);
	String delete(User user);
	User retrieveByUsername(String username);
	User retrieveByEmail(String email);
	List<User> retrieveAll();
	
	String sendForgetPasswordEmail(User user);
	String updatePassword(User user);
}
