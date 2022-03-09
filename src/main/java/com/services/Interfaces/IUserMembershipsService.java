package com.services.Interfaces;

import java.util.List;

public interface IUserMembershipsService {
	String addEmployeeToCompany(List<String> employeesEmails, String companyEmail);
	String addCompanyToEmployee(String employeeEmail, String companyEmail);

	
}
