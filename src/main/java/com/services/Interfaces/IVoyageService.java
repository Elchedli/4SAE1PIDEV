package com.services.Interfaces;

import java.util.List;
import java.util.Optional;

import com.entities.Voyage;

public interface IVoyageService {
	void ajoutVoyage(Voyage voyage);
	void suppVoyage(Voyage voyage);
	void modifierVoyage(int id, Voyage voyage);
	List<Voyage> getAllEntreprises();
	Optional<Voyage> getEntrepriseById(int id);
	Voyage findByUsername(String username);
}
