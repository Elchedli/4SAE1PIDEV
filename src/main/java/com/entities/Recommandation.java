package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.entities.enums.RecommandationAvis;
import com.entities.enums.RecommandationCategory;

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
public class Recommandation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idrecom;
	@NotBlank(message = "Recommandation title is required")
	@Column(unique = true)
	String titreRecom;
	@NotBlank(message = "Image Recommandation is needed")
	String imageRecom;
	@NotBlank(message = "description required")
	@Column(unique = true)
	String description;
	RecommandationCategory recomCategory;
//	Restoration, Antique, Museum, Hotel, Coffees, park, club, House, competition, movie
	RecommandationAvis recomAvis;
//	bas,passable,moyen,bien,excellent
	String country;
//	Afghanistan, Albania, Algeria, Andorra, Angola, Antigua_and_Barbuda, Argentina, Armenia, Australia, Austria, Azerbaijan, Bahamas, Bahrain, Bangladesh, Barbados, Belarus, Belgium, Belize, Benin, Bhutan, Bolivia, Bosnia_and_Herzegovina, Botswana, Brazil, Brunei, Bulgaria, Burkina_Faso, Burundi, Cabo_Verde, Cambodia, Cameroon, Canada, Central_African_Republic, Chad, Chile, China, Colombia, Comoros, Congo, Democratic_Republic_of_the_Congo, Republic_of_the, Costa_Rica, Côte_dIvoire, Croatia, Cuba, Cyprus, Czech_Republic, Denmark, Djibouti, Dominica, Dominican_Republic, East_Timor, Ecuador, Egypt, El_Salvador, Equatorial_Guinea, Eritrea, Estonia, Eswatini, Ethiopia, Fiji, Finland, France, Gabon, Gambia, Georgia, Germany, Ghana, Greece, Grenada, Guatemala, Guinea, Guinea_Bissau, Guyana, Haiti, Honduras, Hungary, Iceland, India, Indonesia, Iran, Iraq, Ireland, Israel, Italy, Jamaica, Japan, Jordan, Kazakhstan, Kenya, Kiribati, North_Korea, South_Korea, Kosovo, Kuwait, Kyrgyzstan, Laos, Latvia, Lebanon, Lesotho, Liberia, Libya, Liechtenstein, Lithuania, Luxembourg, Madagascar, Malawi, Malaysia, Maldives, Mali, Malta, Marshall_Islands, Mauritania, Mauritius, Mexico, Micronesia, Federated_States_of_Moldova, Monaco, Mongolia, Montenegro, Morocco, Mozambique, Myanmar, Namibia, Nauru, Nepal, Netherlands, New_Zealand, Nicaragua, Niger, Nigeria, North_Macedonia, Norway, Oman, Pakistan, Palau, Panama, Papua_New_Guinea, Paraguay, Peru, Philippines, Poland, Portugal, Qatar, Romania, Russia, Rwanda, Saint_Kitts_and_Nevis, Saint_Lucia, Saint_Vincent_and_the_Grenadines, Samoa, San_Marino, Sao_Tome_and_Principe, Saudi_Arabia, Senegal, Serbia, Seychelles, Sierra_Leone, Singapore, Slovakia, Slovenia, Solomon_Islands, Somalia, South_Africa, Spain, Sri_Lanka, Sudan, South_Sudan, Suriname, Sweden, Switzerland, Syria, Taiwan, Tajikistan, Tanzania, Thailand, Togo, Tonga, Trinidad_and_Tobago, Tunisia, Turkey, Turkmenistan, Tuvalu, Uganda, Ukraine, United_Arab_Emirates, United_Kingdom, United_States, Uruguay, Uzbekistan, Vanuatu, Vatican_City, Venezuela, Vietnam, Yemen, Zambia, Zimbabwe
// administrateur va avoir plusieurs ecommandations @ManyToMany
}