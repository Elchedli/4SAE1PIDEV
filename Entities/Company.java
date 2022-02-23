package tn.esprit.main.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Company implements Serializable {
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
