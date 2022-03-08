package com.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class User implements Serializable, UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@NotBlank(message = "Username required.")
	String username;
	@NotBlank(message = "Email required.")
	@Email(message = "Email not valid.")
	String email;
	@NotBlank(message = "Password required")
	@Column(length = 100)
	@Size(min = 8, max = 60, message = "password must have 8 to 20 caracters.")
	String password;
	@Enumerated(EnumType.STRING)
	Role role;
	Boolean locked = false;
	Boolean enabled = false;
	String resetPasswordToken;
	@OneToMany
	@JsonIgnore
	List<Invitation> invitations;
	@OneToOne
	@JsonIgnore
	User company;
	@OneToMany
	@JsonIgnore
	List<User> employees;
	
	/*****************Meriem*********************************/
	@JsonIgnore
	@OneToMany(mappedBy="entreprise")
	List<Voyage> voyagesEntre;
    @JsonIgnore
	@ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
	List<Voyage> voyagesEmpl;
    @JsonIgnore
    @ManyToMany
    private List<User> friends;
	/***********************Oussema***********************************/
    @JsonIgnore
	@OneToOne
	Profile profile;
    /***********************SAMI***********************************/
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL,mappedBy="owner")
    private Set<Post> post;
    @OneToMany(cascade=CascadeType.ALL,mappedBy="user")
    private Set<Comment> comment;
	

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password, Role role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());
		return Collections.singletonList(authority);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}


