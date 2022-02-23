package tn.esprit.main.Services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.main.Entities.Company;
import tn.esprit.main.Repositories.companyRepository;

@Service
public class companyServiceImpl implements companyService {

	@Autowired
	companyRepository cr;
	
	@Override
	public void ajouterCompany(Company company) {
		cr.save(company);
		
	}

	@Override
	public List<Company> retriveAllCompanys() {
		List<Company> listCompanies = (List<Company>) cr.findAll();
		return listCompanies;
	}

	@Override
	public void deleteCompany(int id) {
		cr.deleteById(id);
		
	}

	@Override
	public Company updateCompany(Company company) {
		cr.save(company);
		return company;
	}

	@Override
	public Company updateCompanyPhoto(Company company) {
		
		return null;
	}

	@Override
	public Company retriveCompanyById(int id) {
		Company c = cr.findById(id).get();
		return c;
	}

	@Override
	public Company retriveCompanyByName(String nomCompany) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Company retriveCompanyByName(String nomCompany) {
//		String c = cr.getClass().getName().toString();
//		//Query = 'Select * from Company where nom = nomCompany';
//		
//		return c;
//	}

}
