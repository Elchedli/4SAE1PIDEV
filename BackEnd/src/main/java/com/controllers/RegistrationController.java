package com.controllers;

import javax.validation.Valid;

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

	@PostMapping("/admin")
	public String add(@Valid @RequestBody User user) {
		return registrationService.addAdmin(user);
	}
	
	@PostMapping("/company")
	public String addCompany(@Valid @RequestBody User company) {
		return registrationService.addCompany(company);
	}

	@PostMapping("/employee")
	public String addEmployee(@Valid @RequestBody User employee) {
		return registrationService.addEmployee(employee);
	}

	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token) {
		return confirmationTokenService.confirmToken(token);
	}
}
