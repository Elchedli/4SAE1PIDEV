package com.services.Implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Voyage;
import com.repositories.IVoyageRepository;
import com.services.Interfaces.IVoyageService;


@Service
public class VoyageServiceImpl implements IVoyageService {

	@Autowired
	IVoyageRepository voyageRepository;
	
	@Override
	public void ajoutVoyage(Voyage voyage) {
		voyageRepository.save(voyage);		
	}

	@Override
	public void suppVoyage(Voyage voyage) {
		voyageRepository.delete(voyage);	
		
	}

	@Override
	public void modifierVoyage(int id, Voyage voyage) {
		
	//	voyageRepository.updateVoyage(voyage, id);

	}

	@Override
	public List<Voyage> getAllEntreprises() {
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
		return voyages;
	}

	@Override
	public Optional<Voyage> getEntrepriseById(int id) {
		return voyageRepository.findById(id)	;
	}

	@Override
	public Voyage findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
