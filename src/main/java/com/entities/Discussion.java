package com.entities;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.enums.LastSender;

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
	String ref_disc;
	@Temporal(TemporalType.DATE)
	Date dateDiscussion;
	boolean vue_disc;
	LastSender lastSender;
	
	@ManyToMany(mappedBy = "discPartners") //only two people
	List<Profile> talkers;
	
	@OneToMany(mappedBy = "discussion")
	List<Messa> messages;
}