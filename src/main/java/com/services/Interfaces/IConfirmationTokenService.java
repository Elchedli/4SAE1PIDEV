package com.services.Interfaces;

import java.util.Optional;

import com.entities.ConfirmationToken;

public interface IConfirmationTokenService {
	void saveConfirmationToken (ConfirmationToken confirmationToken);
	Optional<ConfirmationToken> getToken(String token);
	int setConfirmedAt(String token) ;
}
