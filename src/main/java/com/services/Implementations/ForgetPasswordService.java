package com.services.Implementations;

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
	
	public String sendEmail(String email) {
		String msg = "";
		Boolean ExistsByEmail = userRepository.existsByEmail(email);
		if (!ExistsByEmail)
			msg = "Email not found.";
		else {
			String username = userRepository.findByEmail(email).getUsername();
			String link = "http://localhost:8083/voyageAffaires/user/updatePassword";
			emailService.send(email, "Forget Password",
					emailService.buildEmail(username, "Click on the below link to update your password:", link));
			msg = "Click on the link on your email to update your password";
		}
		return msg;
	}

	public String updatePassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.updatePassword(user.getPassword(), user.getEmail());
		return "Password updated";
	}
}
