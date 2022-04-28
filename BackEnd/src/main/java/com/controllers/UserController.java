package com.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.services.Implementations.UserService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
	@Autowired
	UserService userService;

	@PutMapping("/update")
	public String update(@Valid @RequestBody User user) {
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
	@ResponseBody
	public List<User> retrieveAll() {
		return userService.retrieveAll();
	}

	@GetMapping("/loadUserByUsername")
	public UserDetails loadUserByUsername(String username) {
		return userService.loadUserByUsername(username);
	}
	
	@PutMapping("/lockUser")
	public String lockUser(@RequestBody User user) {
		return userService.lockUser(user.getEmail());
	}
	
	@PutMapping("/unlockUser")
	public String unlockUser(@RequestBody User user) {
		return userService.unlockUser(user.getEmail());
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
