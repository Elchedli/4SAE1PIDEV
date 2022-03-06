package com.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.ws.Response;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Utils.FileUploadUtil;
import com.Utils.GeneratePdfReport;
import com.entities.User;
import com.entities.Voyage;
import com.services.Interfaces.IVoyageService;


 

@RestController
@RequestMapping("/voyage")
public class VoyageController {

	@Autowired
	IVoyageService voyageService;
	
	
	/*{
    "departureDate" : "2024-12-12",
	"arrivelDate": "2025-01-05",
	"destination": "Maroc",
	"periode":5,
	"subject":"search",
    "domain":"tech",
    "program":"program",
	"price":50
}*/
	@PostMapping("/addVoyage")
	@ResponseBody
	public String ajoutervoyage(@RequestPart Voyage voyage,@RequestParam("image") MultipartFile picture) throws IOException{
    String fileName = StringUtils.cleanPath(picture.getOriginalFilename());   
    voyage.setPicture(picture.getBytes());
    String uploadDir = "TripPictures/" + voyage.getDestination();
    FileUploadUtil.saveFile(uploadDir, fileName, picture);
 //   return fileName;
	return voyageService.ajoutVoyage(voyage);
	}
	
	@GetMapping("/getVoyages")
	@ResponseBody
	public List<Voyage> getAllVoyages(){
		return voyageService.getAllVoyage();
	}
	
	@PutMapping("/updateVoyage/{idVoyage}")
	@ResponseBody
	public String UpdateVoyage(@PathVariable("idVoyage") int idVoyage,@RequestBody Voyage voyage){
		 return voyageService.modifierVoyage(idVoyage, voyage);	
	}
	
	@DeleteMapping("/deleteVoyage/{idVoyage}")
	@ResponseBody
	public String deleteVoyage(@PathVariable("idVoyage") int idVoyage){
	     return	voyageService.suppVoyage(idVoyage);
	}
	
	@GetMapping("/findVDestionation/{destination}")
	@ResponseBody
	public List<Voyage> GetVoyageByDestination(@PathVariable("destination") String destination){
		 return voyageService.findByDestination(destination);
	}
	
	
	@GetMapping("/date/{month}/{year}")
	@ResponseBody
	public  List<Voyage> FindTripFromDate(@PathVariable("month") String month,@PathVariable("year") int year){
		return voyageService.findVoyageFromdate(month,year);
	}
	
	
	@GetMapping("/priceFromDate/{month}/{year}/{idEntreprise}")
	@ResponseBody
	public  StringBuffer FindPricesfromDate(@PathVariable("month") String month,@PathVariable("year") int year,@PathVariable("idEntreprise") Long idEntreprise){
		return voyageService.getpricefromdate(month, year,idEntreprise);
	}
	
	@GetMapping("/priceRecap/{idEntreprise}")
	@ResponseBody
	public  StringBuffer FindPrices(@PathVariable("idEntreprise") Long idEntreprise){
		return voyageService.getprice(idEntreprise);
	}
	
	
	@PutMapping("/addEmployee/{idVoyage}/{idEmployee}")
	@ResponseBody
	public String AddEmployeetoVoyage(@PathVariable("idVoyage") int idVoyage,@PathVariable("idEmployee") Long id){
		 return voyageService.addEmployeeToVoyage(id, idVoyage);	
	}
	
	@GetMapping("/matchProfession/{idEmployee}")
	@ResponseBody
	public  List<Voyage> FindVoyageFromProfession(@PathVariable("idEmployee") Long idEmployee){
		return voyageService.getVoyageFromProfession(idEmployee);
	}

	@PostMapping("/MatchUsers/{idUserOnline}/{idToMatch}")
	@ResponseBody
	public String ajoutervoyage(@PathVariable("idUserOnline") Long idUserOnline,@PathVariable("idToMatch") Long idToMatch){
	return voyageService.MatchingUsers(idUserOnline, idToMatch);
	}
	
	@GetMapping("/SuggestionUser/{idEmployee}")
	@ResponseBody
	public List<User> SuggestionUser(@PathVariable("idEmployee") Long idEmployee) throws IOException{
	return voyageService.SuggestionUser(idEmployee);
	}
	

	@GetMapping("/afficherPDF/{id}")
	public void eventpdf (@PathVariable("id") Long id) {
			   
		voyageService.eventpdf(id);
	}



}
