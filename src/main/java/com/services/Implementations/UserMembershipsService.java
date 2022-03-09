package com.services.Implementations;

import java.util.List;

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
	public String addEmployeeToCompany(List<String> employeesEmails, String companyEmail) {
		User company = userService.retrieveByEmail(companyEmail);
		for(String s : employeesEmails){
			User exists = userService.retrieveByEmail(s);
			if(exists != null){
				company.getEmployees().add(exists);
				exists.setCompany(company);
			} else{
				String link = "http://localhost:8083/voyageAffaires/registration/addEmployee";
				emailService.send(s, "Sign up to join us.", emailService.buildEmail(s, "Click to the link to join us", link));
			}
		}
		return "employees has joined your company";
	}
}
