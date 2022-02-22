package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Voyage;
import com.services.Interfaces.IVoyageService;


 

@RestController
@RequestMapping("/voyage")
public class VoyageController {

	@Autowired
	IVoyageService voyageService;
	
	@PostMapping("/addVoyage")
	public void ajoutervoyage(@RequestBody Voyage voyage){
		voyageService.ajoutVoyage(voyage);
	}
	
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
	@PutMapping("/updateVoyage/{idVoyage}")
	public void AjouterAppAF(@PathVariable("idVoyage") int idVoyage,@RequestBody Voyage voyage){
		voyageService.modifierVoyage(idVoyage, voyage);
		
	}
}
