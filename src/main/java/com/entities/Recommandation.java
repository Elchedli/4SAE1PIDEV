package com.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.enums.RecommandationAvis;
import com.enums.RecommandationCategory;

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
public class Recommandation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idrecom;
	String titreRecom;
	String ImageRecom;
	String localisation;
	String description;
	RecommandationCategory recomCategory;
	RecommandationAvis recomAvis;
	// administrateur va avoir plusieurs ecommandations @ManyToMany
}