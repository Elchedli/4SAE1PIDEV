package com.entities;

import java.io.Serializable;
import java.util.Locale.Category;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.enums.State;
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
public class Post implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private Category category;
	@Enumerated(EnumType.STRING)
	private State state;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Comment> comment;
	@JsonIgnore
	@ManyToOne
	private User owner;
	private String followers;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Tag> tag;

	// private Set<User> subscribers;

	public void addComment(Comment c) {
		this.comment.add(c);
	}

}
