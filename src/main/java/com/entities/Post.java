package com.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.entities.enums.Category;
import com.entities.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
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
@NotBlank(message = "Title is necessary")
@Column(length = 100)
private String title;
@NotBlank(message = "Description is necessary")
private String description;
@Enumerated(EnumType.STRING)
private Category category;
@Enumerated(EnumType.STRING)
private State state;


@OneToMany(cascade = CascadeType.ALL,mappedBy="post")
private Set<Comment> comment;
@JsonIgnore
//@NotBlank(message = "Owner must be included")
@ManyToOne
private User owner;
private String followers;
@ManyToMany(cascade=CascadeType.ALL)
@JsonIgnore

private Set<Tag> tag;
@Lob()
byte[] file;
//private Set<User> subscribers;

public void addComment(Comment c)
{
	this.comment.add(c);
}


}
