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

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	ConfirmationTokenService confirmationTokenService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = retrieveByUsername(username);
		if(user.isEnabled() && user.isAccountNonLocked()){
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		} 
		return null;
	}

	

	@Override
	public String update(User user) {
		String msg = "";
		Boolean Exists = userRepository.existsByEmail(user.getEmail());
		if (Exists) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.update(user.getUsername(), user.getPassword(), user.getEmail());
			msg = "User updated.";
		} else
			msg = "User not found.";
		return msg;
	}

	@Override
	public String delete(User user) {
		String msg = "";
		User exists = retrieveByUsername(user.getUsername());
		confirmationTokenService.deleteToken(exists);
		if (exists != null) {
			userRepository.delete(exists);
			msg = "User deleted.";
		} else
			msg = "User not found.";
		return msg;
	}

	@Override
	public User retrieveByUsername(String username) {
		Boolean ExistsByUsername = userRepository.existsByUsername(username);
		if (ExistsByUsername)
			return userRepository.findByUsername(username);
		else
			return null;
	}

	@Override
	public User retrieveByEmail(String email) {
		Boolean ExistsByEmail = userRepository.existsByEmail(email);
		if (ExistsByEmail)
			return userRepository.findByEmail(email);
		else
			return null;
	}

	@Override
	public List<User> retrieveAll() {
		return (List<User>) userRepository.findAll();
	}
	
	public String lockUser(String email) {
		userRepository.lockUser(email);
		return "User locked";
	}

	public String unlockUser(String email) {
		userRepository.unlockUser(email);
		return "User unlocked";
	}
}
