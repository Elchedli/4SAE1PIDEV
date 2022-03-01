package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.services.Implementations.ConfirmationEmailService;
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
	@Autowired
	ConfirmationEmailService confirmationEmailService;
	
	@PostMapping("/add")
	public String add(@RequestBody User user) {
		return userService.add(user);
	}
	
	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token) {
		return confirmationEmailService.confirmToken(token);
	}
	
	@PostMapping("/forgetPassword")
	public String forgetPassword(@RequestBody User user) {
		return userService.forgetPasswordEmail(user);
	}
	
	
	@PutMapping("/update")
	public String update(@RequestBody User user) {
		return userService.update(user);
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestBody User user) {
		return userService.delete(user);
	}
	
	@GetMapping("/retrieveByUsername")
	public User retrieveByUsername(@RequestBody UsernameEmail usernameEmail) {
		return userService.retrieveByUsername(usernameEmail.getUsername());
	}
	
	@GetMapping("/retrieveByEmail")
	public User retrieveByEmail(@RequestBody UsernameEmail usernameEmail) {
		return userService.retrieveByEmail(usernameEmail.getEmail());
	}
	
	@GetMapping("/retrieveAll")
	public List<User> retrieveAll() {
		return userService.retrieveAll();
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
