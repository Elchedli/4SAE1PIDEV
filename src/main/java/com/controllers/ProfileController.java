package com.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import com.entities.Profile;
import com.services.Implementations.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	ProfileService pr;
	// @Autowired
	// CompanyService crr;

	@GetMapping("/retrieve-all-Profile")
	public String getProfiles(Model model) {
		List<Profile> listProfile = pr.retriveAllProfiles();
		model.addAttribute("profile", listProfile);
		System.out.println(listProfile);
		return "Profile/List";
	}

	@GetMapping("/retrieve-Profile/{Profile-id}")
	public Profile retrieveProfile(@PathVariable("Profile-id")Long profileId) {

		return pr.retriveProfileById(profileId);
	}

	// Email check
	@PostMapping("/add-profile")
	public String addProfile(@RequestPart Profile p, Model model,@RequestParam("image") MultipartFile multipartFile, HttpServletRequest request)
			throws MessagingException, IOException {
		pr.ajouterProfile(p, getSiteURL(request));
		String siteURL = getSiteURL(request);
		if (pr.isEmailUnique(p.getEmail())) {
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			p.setPhoto(fileName);
			String uploadDir = "Profile-photos/" + p.getNom()+"_"+p.getPrenom();
			FileUpload.saveFile(uploadDir, fileName, multipartFile);
			
			pr.sendVerificationEmail(p, siteURL);
			System.out.print("Mail sent to profile for info verification !");
			return "register/register2";
		} else {
			return "register/register";
		}
	}

	@GetMapping("/verify/{code}")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean verified = pr.verify(code);

		String pageTitle = verified ? "Verification Succeeded !" : "Verification Failed !";
		model.addAttribute("pageTitle", pageTitle);

		return "register/" + (!verified ? "verify_success" : "verify_failed");
	}

	@DeleteMapping("/remove-profile/{profile-id}")
	public String removeProfile(@PathVariable("profile-id") Long profileId, HttpServletRequest request) {
		pr.deleteProfile(profileId, getSiteURL(request));

		String p = "Profile has been deleted !";
		System.out.println(p);
		return "delete/profile_Deleted";

	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	// http://localhost:8089/SpringMVC/data/modify-profile
	@PutMapping("/modify-profile")
	public String modifyProfile(@RequestPart Profile profile,@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		profile.setPhoto(fileName);
		String uploadDir = "Profile-photos/" + profile.getNom()+"_"+profile.getPrenom();
		FileUpload.saveFile(uploadDir, fileName, multipartFile);
		
		pr.updateProfile(profile);
		
		String p = "Profile  " + profile.getNom() +" "+profile.getPrenom()+ " has been modified";
		System.out.println(p);
		return "register/modifyCompany";
	}

	// @GetMapping("/retrieve-all-Companies")
	// @ResponseBody
	// public List<Company> getCompanys() {
	// List<Company> listCompany = crr.retriveAllCompanys();
	// return listCompany;
	// }

	// @GetMapping("/retrieve-Company/{Company-id}")
	// @ResponseBody
	// public Company retrieveCompany(@PathVariable("Company-id") int CompanyId) {
	// return crr.retriveCompanyById(CompanyId);
	// }

	// @PostMapping("/add-Company")
	// @ResponseBody
	// public String addCompany(@RequestPart Company p, HttpServletRequest
	// request/*,@RequestParam("image") MultipartFile picture*/) throws IOException
	// {
	// String fileName = StringUtils.cleanPath(picture.getOriginalFilename());
	// p.setLogo(picture.getBytes());
	// String uploadDir = "CompaniesLogo/" + p.getNameCompany()+ p.getCreatedTime();
	// FileUploadUtil.saveFile(uploadDir, fileName, picture);

//		 crr.ajouterCompany(p, getSiteURL(request));
//		 System.out.print("profile ajouté");
	// return "register_success";
//	}

	// http://localhost:8089/SpringMVC/data/remove-Company/{Company-id}
//	@DeleteMapping("/remove-Company/{Company-id}")
//	@ResponseBody
//	public void removeCompany(@PathVariable("Company-id") int CompanyId) {

	// crr.deleteCompany(CompanyId);
	// }

	// http://localhost:8089/SpringMVC/data/modify-Company
//	@PutMapping("/modify-Company")
	// @ResponseBody
	// public String modifyCompany(@RequestBody Company company) {

//		String p = "Company "+ company.getNameCompany()+" has been modified";
//		crr.updateCompany(company);
//				return p;

//	}

//	@GetMapping("/confirm")
//	public String confirmDelete(@Param("code") String code, Model model) {
//		boolean verified = pr.Confirm(code);
//		
//		String pageTitle = verified ? "Profile Deleted !" : "Delete Failed !";
//		model.addAttribute("pageTitle", pageTitle);
//		
//		return "delete/" + (verified ? "Profile_Deleted" : "Delete_failed");
//	}
	// http://localhost:8089/SpringMVC/data/remove-profile/{profile-id}

}
