package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.services.Implementations.ConfirmationTokenService;
import com.services.Implementations.InvitationService;
import com.services.Implementations.RegistrationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {
	@Autowired
	RegistrationService registrationService;
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	@Autowired
	InvitationService invitationService;
	

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		return registrationService.register(user);
	}

	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token) {
		return confirmationTokenService.confirmToken(token);
	}
	
	@PostMapping("/Admin")
	public String addAdmin(@RequestBody User admin) {
		return registrationService.addAdmin(admin);
	}
	
	@PostMapping("/Employee")
	public String addEmployee(@RequestBody User employee) {
		return registrationService.addEmployee(employee);
	}
	
	@GetMapping("/envoyerInvitation")
	public String envoyerInvitation(@RequestParam("email") String email) {
		return invitationService.envoyerInvitation(email);
	}
	
	
	

}
