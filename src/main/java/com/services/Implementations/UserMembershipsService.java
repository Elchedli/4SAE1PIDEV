package com.services.Implementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Utils.EmailService;
import com.entities.User;
import com.services.Interfaces.IUserMembershipsService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMembershipsService implements IUserMembershipsService {
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;
	
	@Override
	public String addEmployeeToCompany(String employeeEmail, String companyEmail) {
		User companyExists = userService.retrieveByEmail(companyEmail);
		User employeeExists = userService.retrieveByEmail(employeeEmail);
			if(employeeExists != null){
				companyExists.getEmployees().add(employeeExists);
				return "employees has joined your company";
			}
			else{
				String link = "http://localhost:8083/voyageAffaires/registration/addEmployee";
				emailService.send(employeeEmail, "Sign up to join us.", emailService.buildEmail(employeeEmail, "Click to the link to join us", link));
				return "We have sent an invitation to your employee to sign up.";
			}
	}

	@Override
	public String addCompanyToEmployee(String employeeEmail, String companyEmail) {
		User employeeExists = userService.retrieveByEmail(employeeEmail);
		User companyExists = userService.retrieveByEmail(companyEmail);

		employeeExists.setCompany(companyExists);
		return "Company added to and employee";
	}

}
