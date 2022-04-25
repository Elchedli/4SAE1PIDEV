package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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
public class Partner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idPart;
	@NotBlank(message = "company name is needed")
	@Column(unique = true)
	String nomPart;
	@NotBlank(message = "Image logo for the company is needed")
	String logoPart;
	// administrateur va avoir plusieurs partenaires @ManyToMany
}