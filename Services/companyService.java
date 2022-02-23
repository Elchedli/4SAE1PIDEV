package tn.esprit.main.Services;

import java.util.List;

import tn.esprit.main.Entities.Company;

public interface companyService {
	public void ajouterCompany(Company company);
	List<Company> retriveAllCompanys() ;
	void deleteCompany(int id);
	Company updateCompany(Company company);
	Company updateCompanyPhoto(Company company);
	Company retriveCompanyById(int id);
	Company retriveCompanyByName(String nomCompany);
	

}
