package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	//@NotBlank(message = "Program is mandatory")
	String program;
	@Positive
	float price;
	//@JsonIgnore
	//@ManyToOne
//	private Entreprise entreprise;
	//@JsonIgnore
	//@ManyToMany(mappedBy="voyages", cascade=CascadeType.ALL)
	//List<Employee> employees;
}
