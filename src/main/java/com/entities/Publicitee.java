package com.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.enums.PubRegion;
import com.enums.PubType;

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
public class Publicitee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idPub;
	String titrePub;
	String descriptionPub;
	String imagePub;
	PubRegion pubRegion;
	PubType placePub;
	// administrateur va avoir plusieurs publiciée @ManyToMany
}