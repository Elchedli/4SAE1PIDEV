package com.controllers;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Company;
import com.entities.Profile;
import com.repositories.CompanyRepository;
import com.repositories.ProfileRepository;

@RestController
@RequestMapping("/B")
public class GraphController {

	@Autowired
	CompanyRepository cr;

	@Autowired
	ProfileRepository pr;

	@GetMapping("/displayBarGraph")
	private String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		surveyMap.put("France", 40);
		surveyMap.put("Tunisia", 60);
		surveyMap.put("Algeria", 30);
		surveyMap.put("UK", 20);
		model.addAttribute("surveyMap", surveyMap);
		return "barGraph";

	}

	@GetMapping("/displayPieChart")
	private String pieChart() {
		return "";

	}

	@GetMapping("/UserByMonth/{d1}/{d2}")
	public String UserByMonth(@PathVariable String d1, @PathVariable String d2) {
		int c = cr.countCompanyByDate(Date.valueOf(d1), Date.valueOf(d2));
		int p = pr.countProfileByDate(Date.valueOf(d1), Date.valueOf(d2));
		int calcul = p + c;
		String newLine = System.getProperty("line.separator");
		String a = "Users between period " + d2 + " and " + d1 + " are : " + calcul;
		String b = "Companies between period " + d2 + " and " + d1 + " are : " + c;
		String z = "Profiles between period " + d2 + " and " + d1 + " are : " + p;
		return (a + newLine + b + newLine + z);
		// return"Graph/countUser";

	}

	@GetMapping("/UserByCity/{city}")
	public List<Company> companyByCity(@PathVariable String city) {
		List<Company> listCompany = cr.searchByCity(city);
		String newLine = System.getProperty("line.separator");
		System.out.println(" Companies List for " + city + newLine + listCompany + newLine);

		return listCompany;

	}

	@GetMapping("/ProfileByCity/{city}")
	public List<Profile> profileByCity(@PathVariable String city) {
		List<Profile> listProfile = pr.searchByCity(city);
		return listProfile;
	}

	@GetMapping("/countProfileByCity/{city}")
	public String countProfileByCity(@PathVariable String city) {
		int o = pr.countProfileByCity(city);
		int i = cr.countCompanyByCity(city);
		int calcul = o + i;
		String newLine = System.getProperty("line.separator");
		String a = "Users from city : " + city + " are  " + calcul;
		String z = "within Profiles = " + o + " From city : " + city;
		String b = "within Companies = " + i + " From city : " + city;
		return (a + newLine + b + newLine + z);
	}

	@GetMapping("/UserByCountry/{country}")
	public String countProfileByCountry(@PathVariable String country) {
		int o = pr.countProfileByCountry(country);
		int i = cr.countCompanyByCountry(country);
		int calcul = o + i;
		String newLine = System.getProperty("line.separator");
		String a = "Users from country : " + country + " are  " + calcul;
		String z = "within Profiles = " + o + " From country : " + country;
		String b = "within Companies = " + i + " From country : " + country;
		return (a + newLine + b + newLine + z);

	}
	@GetMapping("/searchByCompany/{value}")
	public List<Company> searchByCompany(@PathVariable String value) {
		List<Company> listCompany = cr.search(value);
		return listCompany;
	}
	@GetMapping("/searchByProfile/{value}")
	public List<Profile> searchByProfile(@PathVariable String value) {
		List<Profile> listProfile = pr.search(value);
		return listProfile;
		

	}
	@GetMapping("/search/{value}")
	public String search(@PathVariable String value) {
		List<Profile> listProfile = pr.search(value);
		//List<Company> listCompany = cr.search(value);
		String p = listProfile.toString();
		//Object[] c = listCompany.toArray();
		return p;
		
	}
}
