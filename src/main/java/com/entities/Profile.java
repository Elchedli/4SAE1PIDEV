package com.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Transient;

import com.entities.enums.Countries;
import com.entities.enums.Language;
import com.entities.enums.Salutation;
import com.entities.enums.Sex;
import com.entity.Discussion;

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
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idProfile;
	@NotBlank(message = "username required")
	@Column(unique = true)
	String username;
	String nom;
	String prenom;
	@Enumerated(EnumType.STRING)
	Salutation salutation;
	Date birthdate;
	@Enumerated(EnumType.STRING)
	Sex sex;
	@Enumerated(EnumType.STRING)
	Countries country;
	String city;
	String address;
	String photo;
	@Column(nullable = true)
	int suffix;
	@Column(nullable = true)
	int phone;
	@Column(nullable = true)
	int phone2;
	String bio;
	String profession;
	String nationality;
	String activity;
	@Enumerated(EnumType.STRING)
	Language language;
	
	@Transient
	boolean friend = false;
	
	@OneToOne(mappedBy="profile")
	User utilisateur;
	
	@ManyToMany(mappedBy="conversation",cascade=CascadeType.ALL)
	List<Discussion> discPartners;
}
