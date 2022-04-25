package pi.entity;

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
import pi.enums.PubRegion;
import pi.enums.PubType;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor 
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idPub;
	@NotBlank(message = "Publication title is required")
	String titrePub;
	@NotBlank(message = "Description for the content is required")
	String descriptionPub;
	String imagePub;
	PubRegion pubRegion;
	//GLOBAL,NATIONNAL
	PubType pubType;
	//REMISE,FORFAIT
	// administrateur va avoir plusieurs publiciée @ManyToMany
}