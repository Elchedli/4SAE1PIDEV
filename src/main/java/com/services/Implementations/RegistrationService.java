package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Utils.EmailService;
import com.entities.ConfirmationToken;
import com.entities.User;
import com.entities.enums.Role;
import com.repositories.UserRepository;
import com.services.Interfaces.IRegistrationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationService implements IRegistrationService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ConfirmationTokenService confirmationEmailService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;

	@Override
	public String addAdmin(User user) {
		String msg = "";
		Boolean ExistsByUsername = userRepository.existsByUsername(user.getUsername());
		Boolean ExistsByEmail = userRepository.existsByEmail(user.getEmail());
		if (ExistsByUsername)
			msg = "Username exists.";
		else if (ExistsByEmail)
			msg = "Email exists.";
		else {
		user.setRole(Role.ADMIN);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(5), user);
		confirmationEmailService.add(confirmationToken);

		String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
		emailService.send(user.getEmail(), "Confirm your email.", emailService.buildEmail(user.getUsername(),
				"Thank you for registering. Please click on the below link to activate your account:", link));
		msg = "Company saved, please confirm your email.";
		}
		return msg;
	}
	
	@Override
	public String addCompany(User company) {
		String msg = "";
		Boolean ExistsByUsername = userRepository.existsByUsername(company.getUsername());
		Boolean ExistsByEmail = userRepository.existsByEmail(company.getEmail());
		if (ExistsByUsername)
			msg = "Username exists.";
		else if (ExistsByEmail)
			msg = "Email exists.";
		else {
			company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
			company.setRole(Role.COMPANY);
			userRepository.save(company);

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(5), company);
			confirmationEmailService.add(confirmationToken);

			String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
			emailService.send(company.getEmail(), "Confirm your email.", emailService.buildEmail(company.getUsername(),
					"Thank you for registering. Please click on the below link to activate your account:", link));
			msg = "Company saved, please confirm your email.";
		}
		return msg;
	}

	@Override
	public String addEmployee(User employee) {
		String msg = "";
		Boolean ExistsByUsername = userRepository.existsByUsername(employee.getUsername());
		Boolean ExistsByEmail = userRepository.existsByEmail(employee.getEmail());
		if (ExistsByUsername)
			msg = "Username exists.";
		else if (ExistsByEmail)
			msg = "Email exists.";
		else {
			employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
			employee.setRole(Role.EMPLOYEE);
			userRepository.save(employee);
			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(15), employee);
			confirmationEmailService.add(confirmationToken);

			String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
			emailService.send(employee.getEmail(), "Confirm your email.", emailService.buildEmail(employee.getUsername(),
					"Thank you for registering. Please click on the below link to activate your account:", link));
			msg = "Employee saved, please confirm your email.";
		}
		return msg;
	}

	@Override
	public int enableUser(String email) {
		return userRepository.enableUser(email);
	}
}
