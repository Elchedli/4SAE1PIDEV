package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.services.Implementations.UserService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
	@Autowired
	UserService userService;
	
	@PutMapping("/updateUser")
	public String updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestBody User user) {
		return userService.deleteUser(user);
	}
	
	@GetMapping("/retrieveUserByUsername")
	public User retrieveUserByUsername(@RequestBody UsernameEmail usernameEmail) {
		return userService.retrieveUserByUsername(usernameEmail.getUsername());
	}
	
	@GetMapping("/retrieveUserByEmail")
	public User retrieveUserByEmail(@RequestBody UsernameEmail usernameEmail) {
		return userService.retrieveUserByEmail(usernameEmail.getEmail());
	}
	
	@GetMapping("/retrieveAllUsers")
	public List<User> retrieveAllUsers() {
		return userService.retrieveAllUsers();
	}
	
	@GetMapping("/loadUserByUsername")
	public UserDetails loadUserByUsername(String username) {
		return userService.loadUserByUsername(username);
	}
	
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UsernameEmail {
	String username;
	String email;
}
