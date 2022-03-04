package com.services.Interfaces;

import com.entities.User;

public interface IForgetPasswordService {
	String sendEmail(String email);

	String updatePassword(User user);
}
