package tn.esprit.main.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
public class Profile implements Serializable{
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
	
	

}
