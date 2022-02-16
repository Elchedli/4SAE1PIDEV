package examin.entity;

import java.util.Date;

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
public class Messa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String contenu_msg;
	@Temporal(TemporalType.DATE)
	Date datetemps_msg;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "messages")
	Discussion discussion;
//	@Enumerated(EnumType.STRING)
//	TypeEtudiant typeEtudiant;
//	@JsonIgnore
//	@OneToMany(mappedBy="dataCenter", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etudiant")
//	List<Document> documents;
}