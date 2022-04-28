package com.services.Interfaces;

import com.entities.User;

public interface IForgetPasswordService {
	String updateResetPasswordToken(String email);
	
	User retrieveByResetPasswordToken(String resetPasswordToken);
	
	String updatePassword(User user, String newPassword);
}
