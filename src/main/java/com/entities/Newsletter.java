package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.entities.enums.ArticleRegion;

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
	@Column(unique = true,nullable = false)
	String titreArticle;
	@Column(unique = true,nullable = false)
	String descriptionArticle;
	@Column(nullable = false)
	String imageArticle;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date datenews;
	@Column(nullable = false)
	ArticleRegion articleRegion;
//	International,Nationnal,Regionnal
	String country;
	String city;
	int vues;
	String Publishedago;
	// administrateur va avoir plusieurs articles @ManyToMany
//	@PrePersist
//	void onCreate(){
//		createDate = new Date();
//	}
}