package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.services.Implementations.ForgetPasswordService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/forgetPassword")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgetPasswordController {
	@Autowired
	ForgetPasswordService forgetPasswordService;
	
	@PostMapping("/forgetPassword")
	public String forgetPassword(@RequestBody User user) {
		return forgetPasswordService.sendEmail(user.getEmail());
	}
	@PutMapping("/updatePassword")
	public String updatePassword(@RequestBody User user) {
		return forgetPasswordService.updatePassword(user);
	}
}
