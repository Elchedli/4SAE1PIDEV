package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Voyage;
import com.services.Interfaces.IVoyageService;

 

@RestController
@RequestMapping("/voyage")
public class VoyageController {

	@Autowired
	IVoyageService voyageService;
	
	@PostMapping("/add_voyage")
	public void ajoutervoyage(@RequestBody Voyage voyage){
		voyageService.ajoutVoyage(voyage);
	}
}
