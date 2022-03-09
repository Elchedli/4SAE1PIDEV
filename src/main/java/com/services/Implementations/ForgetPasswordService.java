package com.services.Implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Utils.EmailService;
import com.entities.User;
import com.repositories.UserRepository;
import com.services.Interfaces.IForgetPasswordService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgetPasswordService implements IForgetPasswordService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;
	@Override
	public String updateResetPasswordToken(String email) {
		String msg = "";
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String token = UUID.randomUUID().toString();
			user.setResetPasswordToken(token);
			userRepository.save(user);
			msg = "password token saved.";
			String link = "http://localhost:8083/voyageAffaires/forgetPassword/reset_password?token=" + token;
			emailService.send(email,"Forget Password", emailService.buildEmail(email, "Click to the link to update your password", link));

		} else
			msg = "user not found";
		return msg;
	}
	@Override
	public User retrieveByResetPasswordToken(String resetPasswordToken){
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}
	@Override
	public String updatePassword(User user, String newPassword){
		User UserExist = userRepository.findByEmail(user.getEmail());
		UserExist.setPassword(bCryptPasswordEncoder.encode(newPassword));
		UserExist.setResetPasswordToken(null);
		userRepository.save(UserExist);
		return "password updated";
	}
}
