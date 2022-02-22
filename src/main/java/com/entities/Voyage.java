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
	private Date dateSortie;
	@Temporal(TemporalType.DATE)
	private Date dateArrivee;
	@Temporal(TemporalType.DATE)
	private Date dateRetour;
	String destination;
	String lieuDepart;
	String titreVoyage;
	String domain;
	@Column(length = 9999999)
	String programme;
	float tarif;
	//@JsonIgnore
	//@ManyToOne
//	private Entreprise entreprise;
	//@JsonIgnore
	//@ManyToMany(mappedBy="voyages", cascade=CascadeType.ALL)
	//List<Employee> employees;
	
}
