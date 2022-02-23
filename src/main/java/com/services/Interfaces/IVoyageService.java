package com.services.Interfaces;

import java.util.List;
import java.util.Optional;

import com.entities.Voyage;

public interface IVoyageService {
	String ajoutVoyage(Voyage voyage);
	String suppVoyage(int id);
	String modifierVoyage(int id, Voyage voyage);
	List<Voyage> getAllVoyage();
	Optional<Voyage> getVoyageById(int id);
	List<Voyage> findByDestination(String destination);
}
