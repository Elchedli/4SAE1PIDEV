package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	@NotBlank(message = "You need a content")
private String content;
//@Value("${some.key:0}")
private int up=0;
@Temporal(TemporalType.DATE)
private Date date= new Date(System.currentTimeMillis());


@ManyToOne
//@JsonIgnore
@JsonIgnoreProperties("comment")
private Post post;
@ManyToOne
@JsonIgnoreProperties("comment")
private User user;




}
