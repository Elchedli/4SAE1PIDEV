package pi.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

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
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date dateAd;
	PubRegion pubRegion;
	//GLOBAL,NATIONNAL
	PubType pubType;
	//REMISE,FORFAIT
	// administrateur va avoir plusieurs publiciée @ManyToMany
	String publishedago;
}