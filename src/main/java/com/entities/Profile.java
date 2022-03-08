package com.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.Transient;

import com.entities.enums.Countries;
import com.entities.enums.Language;
import com.entities.enums.Salutation;
import com.entities.enums.Sex;
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
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idProfile;
	@NotBlank(message = "username required")
	@Column(unique = true)
	String username;
	
	
	@NotBlank(message = " LastName is required")
	String nom;
	@NotBlank(message = " FirstName is required")
	String prenom;
	@NotBlank(message = " Email is required")
	@Email
	String Email;
	@Enumerated(EnumType.STRING)
	Salutation salutation;
	//@NotEmpty(message = "Birthdate is required")
	@Temporal(TemporalType.DATE)
	Date birthdate;
	@Enumerated(EnumType.STRING)
	Sex sex;
	@Enumerated(EnumType.STRING)
	Countries country;
	String city;
	String address;
	String photo;
	int suffix;
	//@NotEmpty(message = " Phone number is required")
	int phone;
	int phone2;
	String bio;
	String profession;
	String nationality;
	String activity;
	boolean deleted;
	boolean enabled;
	@Enumerated(EnumType.STRING)
	Language language;
	@Column(name="createdTime", updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	@Column(name="verificationCode", updatable = false)
	private String verificationCode;
	@Column(name="deleteCode", updatable = false)
	private String deleteCode;
	@OneToMany(fetch = FetchType.EAGER ,cascade=CascadeType.REMOVE)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Image> picture;
	
	
	@Transient
	boolean friend = false;
	
	@OneToOne(mappedBy="profile")
	User utilisateur;
	
	@ManyToMany(mappedBy="conversation",cascade=CascadeType.ALL)
	List<Discussion> discPartners;
}
