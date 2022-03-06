package com.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/forgot_password")
	public String processForgotPassword(String email) {
		return forgetPasswordService.updateResetPasswordToken(email);
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token) {
		String msg;
		User user = forgetPasswordService.retrieveByResetPasswordToken(token);
		if (user == null)
			msg = "invalid Token";
		else
			msg = "interface change your password";
		return msg;
	}
	
	@PutMapping("/updatePassword")
	public String updatePassword(@RequestBody User user) {
		return forgetPasswordService.updatePassword(user, user.getPassword());
	}
}
