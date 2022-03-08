package com.services.Interfaces;

import java.util.List;

import com.entities.User;

public interface IUserService {
	String update(User user);

	String delete(User user);

	User retrieveByUsername(String username);

	User retrieveByEmail(String email);

	List<User> retrieveAll();
}
