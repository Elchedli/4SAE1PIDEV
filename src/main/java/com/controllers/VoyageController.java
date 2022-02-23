package com.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Voyage;
import com.services.Interfaces.IVoyageService;


 

@RestController
@RequestMapping("/voyage")
public class VoyageController {

	@Autowired
	IVoyageService voyageService;
	
	@PostMapping("/addVoyage")
	@ResponseBody
	public String ajoutervoyage(@RequestBody Voyage voyage){
		 return voyageService.ajoutVoyage(voyage);
	}
	
	//@ResponseStatus(value = HttpStatus.OK, reason = "this is the reason")
	@GetMapping("/getVoyages")
	@ResponseBody
	public List<Voyage> getAllVoyages(){
		return voyageService.getAllVoyage();
	}
	
	
	@GetMapping("/findVDestionation/{destination}")
	@ResponseBody
	public List<Voyage> GetVoyageByDestination(@PathVariable("destination") String destination){
		 return voyageService.findByDestination(destination);
	}
	
	
	@GetMapping("/findV/{id}")
	@ResponseBody
	public Optional<Voyage> GetVoyageByTitle(@PathVariable("id") int id){
		 return voyageService.getVoyageById(id);
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
}
