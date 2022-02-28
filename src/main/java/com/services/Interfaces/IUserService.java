package com.services.Interfaces;

import java.util.List;


import com.entities.User;

public interface IUserService {
	String updateUser(User user);
	String deleteUser(User user);
	User retrieveUserByUsername(String username);
	User retrieveUserByEmail(String email);
	List<User> retrieveAllUsers();
}
