package com.services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Utils.EmailValide;
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
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public String addUser(User user) {
		String msg = "";
		User userExistsU = retrieveUserByUsername(user.getUsername());
		User userExistsE = retrieveUserByEmail(user.getEmail());

		if (userExistsU != null) {
			msg = "There is already a user registered with the username provided";
			log.error("User {} already exists", user.getUsername());
		} else if (userExistsE != null) {
			msg = "There is already a user registered with the email provided";
			log.error("User {} already exists", user.getEmail());
		} else {
			if (EmailValide.verifierEmail(user.getEmail())) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userRepository.save(user);
				msg = "user saved";
				log.info("User {} saved.", user.getUsername());
			} else {
				msg = "this email is not valid";
				log.error("No valid email for User {}.", user.getUsername());
			}
		}
		return msg;
	}

	@Override
	public String updateUser(User user) {
		String msg = "";
		// User userExistsU = retrieveUserByUsername(user.getUsername());
		User userExistsE = retrieveUserByEmail(user.getEmail());

		if (userExistsE != null) {
			if (userExistsE.getUsername().equals(user.getUsername())) {
				userRepository.updateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
						user.getRole(), user.getEmail());
				msg = "user updated";
				log.info("user updated");
			} else {
				msg = "username is not available";
				log.error("username {} is not available", user.getUsername());
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
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
				user.isAccountNonLocked(), user.getAuthorities());
	}

}
