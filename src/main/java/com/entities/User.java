package com.entities;



import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String email;
private String pwd;
private String fName;
private String lName;
@JsonIgnore
@OneToMany(cascade=CascadeType.ALL,mappedBy="owner")
private Set<Post> post;
@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
private Set<Comment> comment;

}
