package examin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long matricule;
	String prenom;
	@Temporal(TemporalType.DATE)
	Date date;
//	@Enumerated(EnumType.STRING)
//	TypeEtudiant typeEtudiant;
//	@JsonIgnore
//	@OneToMany(mappedBy="dataCenter", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etudiant")
//	List<Document> documents;
}