package com.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invitation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@NotBlank(message = "Email required.")
	@Email
	String de;
	@NotBlank(message = "Email required.")
	@Email
	String pour;
	@NotBlank(message = "Subject required.")
	String sujet;
	@NotBlank(message = "Message required.")
	String message;
	String image;
	
	public Invitation(String de, String pour, String sujet, String message, String image) {
		super();
		this.de = de;
		this.pour = pour;
		this.sujet = sujet;
		this.message = message;
		this.image = image;

	}
	
	
}
