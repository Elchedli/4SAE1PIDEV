package com.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.enums.Countries;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor 
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Company {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	int idCompany;
	String nameCompany;
	String city;
	@Enumerated(EnumType.STRING)
	Countries country;
	String street;
	int postalCode;
	String webAdress;
	String contactPerson;
	String positionInCompany;
	String logo;
	int phone;
	int fax;
	int suffix;
//	@Enumerated(EnumType.STRING)
//	LegalStatus status;
	Date yearEstablished;
//	@Enumerated(EnumType.STRING)
//	Taille type; 
//	@Enumerated(EnumType.STRING)
//	Language language;
	String activity;
	Countries listReferences;
	
}
