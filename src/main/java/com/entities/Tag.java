package com.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id 
private String tag;
@JsonIgnore
@ManyToMany(cascade=CascadeType.ALL,mappedBy="tag")
private Set<Post> post;


}
