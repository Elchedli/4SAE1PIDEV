package pi.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pi.enums.Countries;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor 
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idProfile;
	String username;
	String nom;
	String prenom;
//	@Enumerated(EnumType.STRING)
//	Salutation salutation;
	Date birthdate;
//	@Enumerated(EnumType.STRING)
//	Sex sex;
	@Enumerated(EnumType.STRING)
	Countries country;
	String city;
	String address;
	String photo;
	int suffix;
	int phone;
	int phone2;
	String bio;
	String profession;
	String nationality;
	String activity;
//	@Enumerated(EnumType.STRING)
//	Language language;
	
	@ManyToMany()
	List<Discussion> discPartners;
}