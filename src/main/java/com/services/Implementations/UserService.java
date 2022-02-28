package com.services.Implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.entities.User;
import com.repositories.UserRepository;
import com.services.Interfaces.IUserService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public String updateUser(User user) {
		String msg = "";
		User userExistsU = retrieveUserByUsername(user.getUsername());
		User userExistsE = retrieveUserByEmail(user.getEmail());

		if (userExistsE != null) {
			if (userExistsU != null) {
				if (userExistsE == userExistsU) {
					userRepository.updateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
							user.getRole(), user.getEmail());
					msg = "user updated";
					log.info("user updated");
				} else {
					msg = "username is not available";
					log.error("username {} is not available", user.getUsername());
				}
			} else {
				userRepository.updateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
						user.getRole(), user.getEmail());
				msg = "user updated";
				log.info("user updated");
			}
		} else {
			msg = "user not found";
			log.error("user not found");
		}
		return msg;
	}

	@Override
	public String deleteUser(User user) {
		String msg = "";
		User userExists = retrieveUserByUsername(user.getUsername());

		if (userExists != null) {
			userRepository.delete(userExists);
			msg = "user deleted";
			log.info("User {} deleted.", user.getUsername());
		} else {
			msg = "user not found";
			log.error("User {} not found.", user.getUsername());
		}
		return msg;
	}

	@Override
	public User retrieveUserByUsername(String username) {
		User userExists = userRepository.findUserByUsername(username);
		if (userExists != null) {
			log.info("User {}", userExists.getUsername());
			return userExists;
		} else {
			log.error("User {} not found.", username);
			return null;
		}
	}

	@Override
	public User retrieveUserByEmail(String email) {
		User userExists = userRepository.findUserByEmail(email);
		if (userExists != null) {
			log.info("User {}", userExists.getEmail());
			return userExists;
		} else {
			log.error("User {} not found.", email);
			return null;
		}
	}

	@Override
	public List<User> retrieveAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = retrieveUserByUsername(username);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
