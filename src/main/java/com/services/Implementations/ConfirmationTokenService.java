package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entities.ConfirmationToken;
import com.entities.User;
import com.repositories.ConfirmationTokenRepository;
import com.services.Interfaces.IConfirmationTokenService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationTokenService implements IConfirmationTokenService {
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	RegistrationService registrationService;

	@Override
	public ConfirmationToken add(ConfirmationToken confirmationToken) {
		return confirmationTokenRepository.save(confirmationToken);
	}

	@Override
	public int updateConfirmedAt(String token) {
		return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}

	@Override
	public Optional<ConfirmationToken> getByToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

	@Override
	@Transactional
	public String confirmToken(String token) {
		String msg = "";
		ConfirmationToken confirmationToken = getByToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null) {
			msg = "email already confirmed";
		} else {
			LocalDateTime expiredAt = confirmationToken.getExpiredAt();
			if (expiredAt.isBefore(LocalDateTime.now())) {
				msg = "token expired";
			} else {
				updateConfirmedAt(token);
				registrationService.enableUser(confirmationToken.getUser().getEmail());
				msg = "confirmed";
			}
		}
		return msg;
	}
	
	@Override
	public void deleteToken(User user){
		ConfirmationToken ct = confirmationTokenRepository.findByUser(user);
		confirmationTokenRepository.delete(ct);
	}

}
