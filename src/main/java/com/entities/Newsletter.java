package com.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.enums.ArticleRegion;

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
public class Newsletter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idNews;
	String titreArticle;
	String descriptionArticle;
	String imageArticle;
	@Temporal(TemporalType.DATE) // normally datetime
	Date dateArticle;
	ArticleRegion articleRegion;
	// administrateur va avoir plusieurs articles @ManyToMany
}