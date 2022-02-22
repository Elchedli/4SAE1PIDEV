package com.services.Interfaces;

import java.util.List;
import java.util.Optional;

import com.entities.Voyage;

public interface IVoyageService {
	void ajoutVoyage(Voyage voyage);
	void suppVoyage(Voyage voyage);
	void modifierVoyage(int id, Voyage voyage);
	List<Voyage> getAllVoyage();
	Optional<Voyage> getVoyageById(int id);
	List<Voyage> findByDestination(String destination);
}
