package examin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class Discussion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String reference;
	@Temporal(TemporalType.DATE)
	Date datetemps_disc;
	String nom_source;
	String nom_destinataire;
	boolean vue_disc;
	@OneToMany(cascade = CascadeType.ALL)
	List<Messa> messages;
//	@OneToMany(mappedBy="employee")
//	List<User> users;
//	@Enumerated(EnumType.STRING)
//	TypeEtudiant typeEtudiant;
//	@JsonIgnore
//	@OneToMany(mappedBy="dataCenter", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etudiant")
//	List<Document> documents;
}