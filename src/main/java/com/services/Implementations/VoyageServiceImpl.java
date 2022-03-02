package com.services.Implementations;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.RelationNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.entities.Voyage;
import com.enums.Role;
import com.entities.User;
import com.repositories.IVoyageRepository;
import com.repositories.UserRepository;
import com.services.Interfaces.IVoyageService;


@Service
public class VoyageServiceImpl implements IVoyageService {

	@Autowired
	IVoyageRepository voyageRepository;
	
	@Autowired
	UserRepository userRepository;
	
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
		    //	System.out.println("ifff");
		    	 msg = "We could not update your trip make sure these are valid : \n";
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
		    	msg="Trip updated successfully";      
		    }
		    return msg;	
		}
		else 
			return "the trip doesn't exist";		
	}

	@Override
	public List<Voyage> getAllVoyage(){
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
	//	System.out.println(voyages);
		return voyages;
	}


	@Override
	public List<Voyage> findByDestination(String destination) {
		return voyageRepository.findByDestination(destination);
	}
	
	@Override
	 public List<Voyage> findVoyageFromdate(String month,int year)
	    {
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
		List<Voyage> voyagesFromMonth = new  ArrayList<Voyage>();
		Calendar calendar = Calendar.getInstance();
		Collections.sort(voyages, new Comparator<Voyage>() {
		    @Override
		    public int compare(Voyage v1, Voyage v2) {
		        return v1.getDepartureDate().compareTo(v2.getDepartureDate());
		    }
		});
		for (Voyage v: voyages)
		{
				calendar.setTime(v.getDepartureDate());
				System.out.println(calendar);
					
				if ((GetNumberFromMonth(month)==calendar.get(Calendar.MONTH))&(year==calendar.get(Calendar.YEAR)))
				{
					voyagesFromMonth.add(v);
				}
		}
		return voyagesFromMonth;
	    }
	
	@Override
	@Transactional
	 public StringBuffer getpricefromdate(String month,int year,Long idEntreprise)
	    {
		User entreprise = userRepository.findById(idEntreprise).orElse(null);
		List<Voyage> voyages = voyageRepository.FindEntreprise(entreprise);
		StringBuffer texteEN = new StringBuffer("COST OF THE ALL TRIPS OF THE COMPANY : ");
		texteEN.append(entreprise.getUsername());
		texteEN.append("\n");
		texteEN.append("YEAR : ");
		texteEN.append(year);
		texteEN.append(" MONTH : ");
		texteEN.append(month);
		texteEN.append("\n");
		texteEN.append("\n");
		
		Calendar calendar = Calendar.getInstance();
		float price=0; 
		for (Voyage v: voyages)
		{
				calendar.setTime(v.getDepartureDate());
					
				if ((GetNumberFromMonth(month)==calendar.get(Calendar.MONTH))&(year==calendar.get(Calendar.YEAR)))
				{
					texteEN.append("TRIP TO ");
					texteEN.append(v.getDestination());
					texteEN.append(" FROM ");
					texteEN.append(v.getDepartureDate());
					texteEN.append(" TO ");
					texteEN.append(v.getArrivelDate());
					texteEN.append(" : ");
					texteEN.append("(Number Of Employee that went to the trip: ");
					texteEN.append( v.getEmployees().size() );
					texteEN.append(") ");
					texteEN.append(v.getPrice()*v.getEmployees().size());
					texteEN.append("\n");
					price = price + (v.getPrice()*v.getEmployees().size());
				}
				
		}
		texteEN.append(" Total Cost :");
		texteEN.append(price);
		return texteEN;
	    }
	
	
	@Override
	@Transactional
	 public StringBuffer getprice(Long idEntreprise)
	    {
		User entreprise = userRepository.findById(idEntreprise).orElse(null);
		List<Voyage> voyages = voyageRepository.FindEntreprise(entreprise);
		StringBuffer texteEN = new StringBuffer("COST OF THE ALL TRIPS OF THE COMPANY : ");
		texteEN.append(entreprise.getUsername());
		texteEN.append("\n");
		texteEN.append("\n");

		float price=0; 
		for (Voyage v: voyages)
		{
					texteEN.append("TRIP TO ");
					texteEN.append(v.getDestination());
					texteEN.append(" FROM ");
					texteEN.append(v.getDepartureDate());
					texteEN.append(" TO ");
					texteEN.append(v.getArrivelDate());
					texteEN.append(" : ");
					texteEN.append("(Number Of Employee that went to the trip: ");
					texteEN.append( v.getEmployees().size() );
					texteEN.append(") ");
					texteEN.append(v.getPrice()*v.getEmployees().size());
					texteEN.append("\n");
					price = price + (v.getPrice()*v.getEmployees().size());
				
		}
		texteEN.append(" Total Cost :");
		texteEN.append(price);
		return texteEN;
	    }
	

	

	 public int GetNumberFromMonth(String Month)
	    {
           
		    DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);

		    String[] joursSemaineEN = dfsEN.getWeekdays();

		    StringBuffer texteEN = new StringBuffer("Jours EN ");

		    for (int i = 1; i < joursSemaineEN.length; i++) {
		      texteEN.append(" : ");
		      texteEN.append(joursSemaineEN[i]);
		    }
		   /* texteEN = new StringBuffer("Mois courts EN ");

		    String[] moisCourtsEN = dfsEN.getShortMonths();

		    for (int i = 0; i < moisCourtsEN.length - 1; i++) {
		      texteEN.append(" : ");
		      texteEN.append(moisCourtsEN[i]);
		    }

		    System.out.println(texteEN);*/

		    texteEN = new StringBuffer("Mois EN ");
		    String[] moisEN = dfsEN.getMonths();

		    for (int i = 0; i < moisEN.length - 1; i++) {
		      texteEN.append(" : ");
		      texteEN.append(moisEN[i]);
			    if (moisEN[i].equalsIgnoreCase(Month))
			    {
			    	return i;
			    }
		    }

		    return -1;
		  }


	@Override
	@Transactional
	public String addEmployeeToVoyage(Long id,int idVoyage)
	    {
			Voyage voyage = voyageRepository.findById(idVoyage).orElse(null);
			User user = userRepository.findById(id).orElse(null);
			List<User> userVoyage = voyage.getEmployees();
			int x=0;
			
			for (User u : userVoyage)
			{
				if (u.getId()==user.getId())
				{
					x=x+1;
				}
			}
			
			if (x==0) 
			{
				if (user.getRole()==Role.EMPLOYEE)
				{
					voyage.getEmployees().add(user);
					return "Employee added successfuly";
				}
				else
				return "This user is not an employee";
			} 
			else	
			return "Employee already has this trip";		
	    }


	
	
	
	/*@Override
	@Transactional
	public String getEmployeefromvoyage(Long id,int idVoyage)
	    {
			Voyage voyage = voyageRepository.findById(idVoyage).orElse(null);
			User user = userRepository.findById(id).orElse(null);
			if (user.getRole()==Role.EMPLOYEE)
			{
				voyage.getEmployees().add(user);
				return "Employee added successfuly";
			}
			else 
			return "Could not add this employee";
	    }*/
	
	
	

	 
}
