package com.controllers;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Utils.DecodeToken;
import com.Utils.FileUpload;
import com.entities.User;
import com.entities.Voyage;
import com.services.Interfaces.IVoyageService;

@RestController
@RequestMapping("/voyage")
public class VoyageController {

	@Autowired
	IVoyageService voyageService;
	@Autowired
	DecodeToken decodeToken;

	/*
	 * { "departureDate" : "2024-12-12", "arrivelDate": "2025-01-05",
	 * "destination": "Maroc", "periode":5, "subject":"search", "domain":"tech",
	 * "program":"program", "price":50 }
	 */
	public User getUser(String token){
		return decodeToken.decode(token);
	}
	@PostMapping("/addVoyage")
	@ResponseBody
	public String ajoutervoyage(@RequestPart Voyage voyage, @RequestParam("image") MultipartFile picture, @RequestParam("token") String token)
			throws IOException {
		String fileName = StringUtils.cleanPath(picture.getOriginalFilename());
		voyage.setPicture(picture.getBytes());
		String uploadDir = "TripPictures/" + voyage.getDestination();
		FileUpload.saveFile(uploadDir, fileName, picture);
		// return fileName;
		voyage.setEntreprise(getUser(token));
		return voyageService.ajoutVoyage(voyage);
	}

	@GetMapping("/getVoyages")
	@ResponseBody
	public List<Voyage> getAllVoyages() {
		return voyageService.getAllVoyage();
	}

	@PutMapping("/updateVoyage/{idVoyage}")
	@ResponseBody
	public String UpdateVoyage(@PathVariable("idVoyage") int idVoyage, @RequestBody Voyage voyage) {
		return voyageService.modifierVoyage(idVoyage, voyage);
	}

	@DeleteMapping("/deleteVoyage/{idVoyage}")
	@ResponseBody
	public String deleteVoyage(@PathVariable("idVoyage") int idVoyage) {
		return voyageService.suppVoyage(idVoyage);
	}

	@GetMapping("/findVDestionation/{destination}")
	@ResponseBody
	public List<Voyage> GetVoyageByDestination(@PathVariable("destination") String destination) {
		return voyageService.findByDestination(destination);
	}

	@GetMapping("/date/{month}/{year}")
	@ResponseBody
	public List<Voyage> FindTripFromDate(@PathVariable("month") String month, @PathVariable("year") int year) {
		return voyageService.findVoyageFromdate(month, year);
	}

	@GetMapping("/priceFromDate/{month}/{year}/{idEntreprise}")
	@ResponseBody
	public StringBuffer FindPricesfromDate(@PathVariable("month") String month, @PathVariable("year") int year,
			@PathVariable("idEntreprise") Long idEntreprise) {
		return voyageService.getpricefromdate(month, year, idEntreprise);
	}

	@GetMapping("/priceRecap/{idEntreprise}")
	@ResponseBody
	public StringBuffer FindPrices(@PathVariable("idEntreprise") Long idEntreprise) {
		return voyageService.getprice(idEntreprise);
	}

	@PutMapping("/addEmployee/{idVoyage}/{idEmployee}")
	@ResponseBody
	public String AddEmployeetoVoyage(@PathVariable("idVoyage") int idVoyage, @PathVariable("idEmployee") Long id) {
		return voyageService.addEmployeeToVoyage(id, idVoyage);
	}

	@GetMapping("/matchProfession/{idEmployee}")
	@ResponseBody
	public List<Voyage> FindVoyageFromProfession(@PathVariable("idEmployee") Long idEmployee) {
		return voyageService.getVoyageFromProfession(idEmployee);
	}

	@PostMapping("/MatchUsers/{idUserOnline}/{idToMatch}")
	@ResponseBody
	public String ajoutervoyage(@PathVariable("idUserOnline") Long idUserOnline,
			@PathVariable("idToMatch") Long idToMatch) {
		return voyageService.MatchingUsers(idUserOnline, idToMatch);
	}

	@GetMapping("/SuggestionUser/{idEmployee}")
	@ResponseBody
	public List<User> SuggestionUser(@PathVariable("idEmployee") Long idEmployee) throws IOException {
		return voyageService.SuggestionUser(idEmployee);
	}

	@GetMapping("/afficherPDF/{id}")
	public void eventpdf(@PathVariable("id") Long id) {

		voyageService.eventpdf(id);
	}

}
