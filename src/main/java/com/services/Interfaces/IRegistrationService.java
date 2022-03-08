package com.services.Interfaces;

import com.entities.User;

public interface IRegistrationService {
	String addCompany(User company);

	String addEmployee(User employee);

	int enableUser(String email);

	String addAdmin(User user);
}
