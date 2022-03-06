package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.Implementations.UserMembershipsService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/UserMemberships")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMembershipsController {
	@Autowired
	UserMembershipsService userMembershipsService;

	@PutMapping("/addEmployeeToCompany")
	public String addEmployeeToCompany(@RequestBody Emails emails) {
		return userMembershipsService.addEmployeeToCompany(emails.getEmployeesEmails(), emails.getCompanyEmail());
	}

	@PutMapping("/addCompanyToEmployee")
	public String addCompanyToEmployee(@RequestBody Emails emails) {
		return userMembershipsService.addCompanyToEmployee(emails.getEmployeeEmail(), emails.getCompanyEmail());
	}

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Emails {
	List<String> employeesEmails;
	String companyEmail;
	String employeeEmail;

}