package tn.esprit.main.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.main.Entities.Company;
import tn.esprit.main.Entities.Profile;
import tn.esprit.main.Services.companyServiceImpl;
import tn.esprit.main.Services.profileServiceImpl;


@RestController
@RequestMapping("/data")

public class REST {

	@Autowired
	profileServiceImpl pr;
	companyServiceImpl crr;
	
	// http://localhost:8089/SpringMVC/data/retrieve-all-Profile
		
		@GetMapping("/retrieve-all-Profile")
		public List<Profile> getProfiles() {
			List<Profile> listProfile = pr.retriveAllProfiles();
			return listProfile;
		}
		@GetMapping("/retrieve-Profile/{Profile-id}")
		public Profile retrieveProfile(@PathVariable("Profile-id") int profileId) {
			return pr.retriveProfileById(profileId);
		}
		@PostMapping("/add-profile")
		public void  addProfile(@RequestBody Profile p) {
			pr.ajouterProfile(p);
		}

		// http://localhost:8089/SpringMVC/data/remove-profile/{profile-id}
		@DeleteMapping("/remove-profile/{profile-id}")
		public void removeProfile(@PathVariable("profile-id") int profileId) {
			pr.deleteProfile(profileId);
		}

		// http://localhost:8089/SpringMVC/data/modify-profile
		@PutMapping("/modify-profile")
		public Profile modifyProfile(@RequestBody Profile profile) {
			return pr.updateProfile(profile);
		}
		@GetMapping("/retrieve-all-Companies")
		public List<Company> getCompanys() {
			List<Company> listCompany = crr.retriveAllCompanys();
			return listCompany;
		}
		@GetMapping("/retrieve-Company/{Company-id}")
		public Company retrieveCompany(@PathVariable("Company-id") int CompanyId) {
			return crr.retriveCompanyById(CompanyId);
		}
		@PostMapping("/add-Company")
		public void  addCompany(@RequestBody Company p) {
			crr.ajouterCompany(p);
		}

		// http://localhost:8089/SpringMVC/data/remove-Company/{Company-id}
		@DeleteMapping("/remove-Company/{Company-id}")
		public void removeCompany(@PathVariable("Company-id") int CompanyId) {
			crr.deleteCompany(CompanyId);
		}

		// http://localhost:8089/SpringMVC/data/modify-Company
		@PutMapping("/modify-Company")
		public Company modifyCompany(@RequestBody Company Company) {
			return crr.updateCompany(Company);
		}

	
	
}
