package com.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.Utils.FileUpload;
import com.entities.Company;
import com.services.Implementations.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService service;
	
	@PostMapping("/add-company")
	public String addCompany(@Valid @RequestPart Company c,@RequestParam("image") MultipartFile multipartFile,Model model, HttpServletRequest request) throws IOException {
		service.ajouterCompany(c, getSiteURL(request));
		String siteURL = getSiteURL(request);
		if (service.isEmailUnique(c.getEmail())) {
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			c.setLogo(fileName);
			String uploadDir = "Companies-photos/" + c.getNameCompany();
			FileUpload.saveFile(uploadDir, fileName, multipartFile);
			
			service.sendVerificationEmail(c, siteURL);
			System.out.println("Mail sent to company for info verification !");
			return "register/register2";
		} else {
			return "register/register";
		}
		

	}
	 
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  

	
	@GetMapping("/verify/{code}")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean verified = service.verify(code);
		
		String pageTitle = verified ? "Verification Succeeded !" : "Verification Failed !";
		model.addAttribute("pageTitle", pageTitle);
		
		return "register/" + (!verified ? "verify_success" : "verify_failed");
	}
	
	@GetMapping("/retrieveAll")
	public String getCompanies(Model model){
		
		List<Company> listCompany = service.retriveAllCompanys();
		System.out.println(listCompany);
		model.addAttribute("company", listCompany);
	
		return "profile/listcompany";
	}
	
	@PutMapping("/modify-company")
	public String modifyCompany( @RequestPart Company c,@RequestParam("image") MultipartFile multipartFile,Model model) throws IOException {
		
		
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		c.setLogo(fileName);
		String uploadDir = "Companies-photos/" + c.getNameCompany();
		FileUpload.saveFile(uploadDir, fileName, multipartFile);
		
		service.updateCompany(c);
		
		String p = "Company "+ c.getNameCompany()+" has been modified";
		System.out.println(p);
		//retour html update
		return "register/modifyCompany";
	}
	
	@DeleteMapping("/delete-company/{company-id}")
	
	private String removeCompany(@PathVariable("company-id") Long companyId) {
		boolean deleted = false ;
		try {
			
			deleted = service.deleteCompany(companyId);
			
		}catch(Exception handlerException) {
			System.out.println("effacé");
			deleted = true ;
		}
	//	service.removeCompany(companyId);
	//	return  "delete/Profile_deleted";
		return deleted ? "delete/Profile_deleted" : "delete/Delete_failed";	
	}
	
	

	
}
