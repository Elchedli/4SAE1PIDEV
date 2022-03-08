package com.services.Interfaces;

import java.util.Optional;

import com.entities.ConfirmationToken;
import com.entities.User;

public interface IConfirmationTokenService {
	ConfirmationToken add(ConfirmationToken confirmationToken);

	Optional<ConfirmationToken> getByToken(String token);

	int updateConfirmedAt(String token);

	String confirmToken(String token);

	void deleteToken(User user);
	
}
