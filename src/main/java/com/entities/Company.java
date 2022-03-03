package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.websocket.Encoder.Binary;

import com.enums.Countries;
import com.enums.Language;
import com.enums.LegalStatus;
import com.enums.Taille;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	String Email;
	String city;
	@Enumerated(EnumType.STRING)
	Countries country;
	String street;
	int postalCode;
	String webAdress;
	String contactPerson;
	String positionInCompany;
	@Lob()
	byte[] logo;
	int phone;
	int fax;
	int suffix;
	@Enumerated(EnumType.STRING)
	LegalStatus status;
	Date yearEstablished;
	@Enumerated(EnumType.STRING)
	Taille type; 
	@Enumerated(EnumType.STRING)
	Language language;
	String activity;
	Countries listReferences;
	@Column(name="createdTime", updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	@Column(name="verificationCode", updatable = false)
	private String verificationCode;
	private boolean enabled;
	@OneToOne(mappedBy="company")
	User utilisateur;
	
}
