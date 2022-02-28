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

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		return registrationService.register(user);
	}

	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token) {
		return confirmationTokenService.confirmToken(token);
	}

}
