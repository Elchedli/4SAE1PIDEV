package pi.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String refdisc;
//	@Column(unique = true)
//	String refdisc;
//	@Column(nullable = false)
//	String sender;
//	@Column(nullable = false)
//	String receiver;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date dateDiscussion;
	LastSender lastSender;
	boolean vue_disc;
	@OneToMany(mappedBy="discussion",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	List<Messa> messages;
	@ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	List<Profile> conversation;
//	@ManyToMany(mappedBy = "discPartners",cascade = CascadeType.ALL) //only two people
//	List<Profile> talkers;
	
}