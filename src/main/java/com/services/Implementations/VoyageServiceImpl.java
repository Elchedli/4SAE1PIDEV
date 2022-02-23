package com.services.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.RelationNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	public String ajoutVoyage(Voyage voyage) {
		String msg = "";
	    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    Set<ConstraintViolation<Voyage>> constraintViolations = 
	      validator.validate(voyage);

	    if ((constraintViolations.size() > 0) | (voyage.getArrivelDate().before(voyage.getDepartureDate()))){
	    	 msg = "We could not add your trip make sure these are valid : \n";
	      for (ConstraintViolation<Voyage> contraintes : constraintViolations) {
	            msg = " " + msg
	    		+ "" + contraintes.getRootBeanClass().getSimpleName()+
	           "." + contraintes.getPropertyPath() + " " + contraintes.getMessage() + "\n";
	      }
	      if (voyage.getArrivelDate().before(voyage.getDepartureDate()))
	      {
	            msg = " " + msg + "Voyage.Date Date of ArrivelDate must be after Date of getDepartureDate";
	      }

	    } else {
	    	voyageRepository.save(voyage);
	    	msg="Trip added successfully";      
	    }
		return msg;		
	}

	@Override
	public String suppVoyage(int id) {
		if (voyageRepository.findById(id).isPresent())
		{
			voyageRepository.delete(voyageRepository.findById(id).get());
			return "The trip is deleted successfully";
		}
		else
			return "The trip doesn't exist";
	}

	@Override
	public String modifierVoyage(int id, Voyage voyage) {
		String msg = "";
		if (voyageRepository.findById(id).isPresent())
		{
		    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		    Validator validator = factory.getValidator();

		    Set<ConstraintViolation<Voyage>> constraintViolations = 
		      validator.validate(voyage);

		    if ((constraintViolations.size() > 0) | (voyage.getArrivelDate().before(voyage.getDepartureDate()))){
		    	System.out.println("ifff");
		    	 msg = "We could not add your trip make sure these are valid : \n";
		      for (ConstraintViolation<Voyage> contraintes : constraintViolations) {
		            msg = " " + msg
		    		+ "" + contraintes.getRootBeanClass().getSimpleName()+
		           "." + contraintes.getPropertyPath() + " " + contraintes.getMessage() + "\n";
		      }
		      if (voyage.getArrivelDate().before(voyage.getDepartureDate()))
		      {
		            msg = " " + msg + "Voyage.Date Date of ArrivelDate must be after Date of getDepartureDate";
		      }

		    } else {
		    	voyage.setIdVoyage(id);
		    	voyageRepository.save(voyage);
		    	msg="Trip added successfully";      
		    }
		    return msg;	
		}
		else 
			return "the trip doesn't exist";		
	}

	@Override
	public List<Voyage> getAllVoyage(){
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
		System.out.println(voyages);
		return voyages;
	}

	@Override
	public Optional<Voyage> getVoyageById(int id) {
		return voyageRepository.findById(id);
	}

	@Override
	public List<Voyage> findByDestination(String destination) {
		return voyageRepository.findByDestination(destination);
	}
	

}
