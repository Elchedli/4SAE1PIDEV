package com.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Transient;

import com.enums.Countries;

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
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idProfile;
	@NotBlank(message = "username required")
	@Column(unique = true)
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
	//	Afghanistan, Albania, Algeria, Andorra, Angola, Antigua_and_Barbuda, Argentina, Armenia, Australia, Austria, Azerbaijan, Bahamas, Bahrain, Bangladesh, Barbados, Belarus, Belgium, Belize, Benin, Bhutan, Bolivia, Bosnia_and_Herzegovina, Botswana, Brazil, Brunei, Bulgaria, Burkina_Faso, Burundi, Cabo_Verde, Cambodia, Cameroon, Canada, Central_African_Republic, Chad, Chile, China, Colombia, Comoros, Congo, Democratic_Republic_of_the_Congo, Republic_of_the, Costa_Rica, Côte_dIvoire, Croatia, Cuba, Cyprus, Czech_Republic, Denmark, Djibouti, Dominica, Dominican_Republic, East_Timor, Ecuador, Egypt, El_Salvador, Equatorial_Guinea, Eritrea, Estonia, Eswatini, Ethiopia, Fiji, Finland, France, Gabon, Gambia, Georgia, Germany, Ghana, Greece, Grenada, Guatemala, Guinea, Guinea_Bissau, Guyana, Haiti, Honduras, Hungary, Iceland, India, Indonesia, Iran, Iraq, Ireland, Israel, Italy, Jamaica, Japan, Jordan, Kazakhstan, Kenya, Kiribati, North_Korea, South_Korea, Kosovo, Kuwait, Kyrgyzstan, Laos, Latvia, Lebanon, Lesotho, Liberia, Libya, Liechtenstein, Lithuania, Luxembourg, Madagascar, Malawi, Malaysia, Maldives, Mali, Malta, Marshall_Islands, Mauritania, Mauritius, Mexico, Micronesia, Federated_States_of_Moldova, Monaco, Mongolia, Montenegro, Morocco, Mozambique, Myanmar, Namibia, Nauru, Nepal, Netherlands, New_Zealand, Nicaragua, Niger, Nigeria, North_Macedonia, Norway, Oman, Pakistan, Palau, Panama, Papua_New_Guinea, Paraguay, Peru, Philippines, Poland, Portugal, Qatar, Romania, Russia, Rwanda, Saint_Kitts_and_Nevis, Saint_Lucia, Saint_Vincent_and_the_Grenadines, Samoa, San_Marino, Sao_Tome_and_Principe, Saudi_Arabia, Senegal, Serbia, Seychelles, Sierra_Leone, Singapore, Slovakia, Slovenia, Solomon_Islands, Somalia, South_Africa, Spain, Sri_Lanka, Sudan, South_Sudan, Suriname, Sweden, Switzerland, Syria, Taiwan, Tajikistan, Tanzania, Thailand, Togo, Tonga, Trinidad_and_Tobago, Tunisia, Turkey, Turkmenistan, Tuvalu, Uganda, Ukraine, United_Arab_Emirates, United_Kingdom, United_States, Uruguay, Uzbekistan, Vanuatu, Vatican_City, Venezuela, Vietnam, Yemen, Zambia, Zimbabwe
	String city;
	String address;
	String photo;
	@Column(nullable = true)
	int suffix;
	@Column(nullable = true)
	int phone;
	@Column(nullable = true)
	int phone2;
	String bio;
	String profession;
	String nationality;
	String activity;
	
	@Transient
	boolean friend = false;
//	@Enumerated(EnumType.STRING)
//	Language language;
	
	@ManyToMany(mappedBy="conversation",cascade=CascadeType.ALL)
	List<Discussion> discPartners;
}