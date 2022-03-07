package com.services.Interfaces;


public interface IUserMembershipsService {
	String addEmployeeToCompany(String employeeEmail, String companyEmail);
	String addCompanyToEmployee(String employeeEmail, String companyEmail);

	
}
