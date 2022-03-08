package com.entities;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.entities.enums.LastSender;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	String refdisc;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	Date dateDiscussion;
	LastSender lastSender;
	boolean vue_disc;
	@OneToMany(cascade=CascadeType.ALL)
//	@ToString.Exclude
	List<Messa> messages;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@ToString.Exclude
	List<Profile> conversation;
	@Transient
	boolean friend;
}