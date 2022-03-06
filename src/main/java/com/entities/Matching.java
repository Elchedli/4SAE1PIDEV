package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Matching implements Serializable{

		private static final long serialVersionUID = 1L;
		//@JsonIgnore
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int idMachhing;
		@JsonIgnore
		@ManyToMany(cascade = CascadeType.ALL)
		List<User> mUsers;
		
		int idOfMatchedUser;
}
