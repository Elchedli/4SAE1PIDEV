package com.services.Interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.entities.Company;



public interface ICompany {
	public void ajouterCompany(Company company, String siteURL);
	List<Company> retriveAllCompanys() ;
	boolean deleteCompany(Long id);
	public void sendVerificationEmail(Company company, String siteURL);
	public boolean isEnabled();
	public boolean isEmailUnique(String email);
	public Company getCompanyByEmail(String email);
	String addPhoto(String title, MultipartFile file);
	Company updateCompany(Company company);
	Company updateCompanyPhoto(Company company);
	Company retriveCompanyById(Long id);
	Company retriveCompanyByName(String nomCompany);
	public String removeCompany(Long id);

}
