package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Voyage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idVoyage;
	@Temporal(TemporalType.DATE)
	@FutureOrPresent(message = "Date must be after today")
	//@NotBlank(message = "All dates are mandatory")
	private Date departureDate;
	@Temporal(TemporalType.DATE)
	//@NotEmpty(message = "All dates are mandatory")
	private Date arrivelDate;
	@NotBlank(message = "Destination is mandatory")
	String destination;
	@NotNull(message = "periode is mandatory")
	int periode;
	@NotBlank(message = "subject is mandatory")
	String subject;
	@NotBlank(message = "Domain is mandatory")
	String domain;
	@Column(length = 9999999)
	@NotBlank(message = "Program is mandatory")
	String program;
	@Lob()
	byte[] picture;
	@Positive
	float price;
	
	@JsonIgnore
	@Where(clause = "role='COMPANY'")
	@ManyToOne
	private User entreprise;

	@JsonIgnore
	@Where(clause = "role='EMPLOYEE'")
	@ManyToMany(cascade = CascadeType.ALL)
	List<User> employees;
}
