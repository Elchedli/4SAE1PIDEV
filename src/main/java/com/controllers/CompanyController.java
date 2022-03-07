package com.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Utils.Utility;
import com.entities.Company;
import com.entities.Profile;
import com.services.Implementations.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService service;
	
	@PostMapping("/add-company")
	public String addCompany(@Valid @RequestBody Company c,Model model, HttpServletRequest request) {
		service.ajouterCompany(c, getSiteURL(request));
		String siteURL = getSiteURL(request);
		
		service.sendVerificationEmail(c, siteURL);
		System.out.println("Mail sent to company for info verification !");
		return "register/register2";

	}
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  

	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean verified = service.verify(code);
		
		String pageTitle = verified ? "Verification Succeeded !" : "Verification Failed !";
		model.addAttribute("pageTitle", pageTitle);
		
		return "register/" + (verified ? "verify_success" : "verify_failed");
	}
	
	@GetMapping("/retrieve-all-companies")
	public List<Company> getCompanies(){
		List<Company> listCompany = service.retriveAllCompanys();
		return listCompany;
	}
	
	@PutMapping("/modify-company")
	public String modifyCompany(@Valid @RequestBody Company c) {
		service.updateCompany(c);
		String p = "Company "+ c.getNameCompany()+" has been modified";
		return p;
	}
	
	

	
}
