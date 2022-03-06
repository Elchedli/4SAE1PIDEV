package com.voyageAffaires.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String username;
	String email;
	String password;
	Boolean locked = false;
	Boolean enabled = false;
	@Enumerated(EnumType.STRING)
	Role role;
	@OneToMany(cascade = CascadeType.ALL)
	List<Invitation> invitations;


	@OneToMany(mappedBy = "user",cascade ={CascadeType.REMOVE,CascadeType.PERSIST} )
	private List<Reclamation> reclamations;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<FeedBack> feedBacks;


}
