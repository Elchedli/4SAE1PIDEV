package com.entities;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String token;
	LocalDateTime localDateTime;
	LocalDateTime expiredAt;
	LocalDateTime confirmedAt;
	@ManyToOne
	User user;

	public ConfirmationToken(String token, LocalDateTime localDateTime, LocalDateTime expiredAt, User user) {
		super();
		this.token = token;
		this.localDateTime = localDateTime;
		this.expiredAt = expiredAt;
		this.user = user;
	}

}
