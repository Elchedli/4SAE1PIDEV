package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.enums.Countries;
import com.enums.Language;
import com.enums.LegalStatus;
import com.enums.Salutation;
import com.enums.Sex;
import com.enums.Taille;


public class Profil implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@Entity
	public class Employee{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idProfile;
	private String nom;
	private String prenom;
	@Enumerated(EnumType.STRING)
	private Salutation salutation ;
	private Date birthdate;
	@Enumerated(EnumType.STRING)
	private Sex sex;
	@Enumerated(EnumType.STRING)
	private Countries country;
	private String city;
	private String address;
	private String photo;
	private int suffix;
	private int phone;
	private int phone2;
	private String bio;
	private String profession;
	private String nationality;
	private String activity;
	@Enumerated(EnumType.STRING)
	private Language language;
	
	
	@Entity
	public class Company{
		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		private int idCompany;
		private String nameCompany;
		private String city;
		@Enumerated(EnumType.STRING)
		private Countries country;
		private String street;
		private int postalCode;
		private String webAdress;
		private String contactPerson;
		private String positionInCompany;
		private String logo;
		private int phone;
		private int fax;
		private int suffix;
		@Enumerated(EnumType.STRING)
		private LegalStatus status;
		private Date yearEstablished;
		@Enumerated(EnumType.STRING)
		private Taille type; 
		@Enumerated(EnumType.STRING)
		private Language language;
		private String activity;
		private Countries listReferences;
		
		
	}
	
	}
}
