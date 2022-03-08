package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class Image implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	private String type ;
	private String image;
	
	@OneToOne
	Company company;
	@OneToOne
	Profile profile;
}
