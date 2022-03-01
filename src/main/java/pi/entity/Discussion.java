package pi.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pi.enums.LastSender;

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
	Integer idisc;
	@Column(unique = true)
	String refdisc;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date dateDiscussion;
	boolean vue_disc;
	LastSender lastSender;
	String sender;
	String receiver;
//	@ManyToMany(mappedBy = "discPartners",cascade = CascadeType.ALL) //only two people
//	List<Profile> talkers;
	@OneToMany(cascade=CascadeType.ALL)
	List<Messa> messages;
}