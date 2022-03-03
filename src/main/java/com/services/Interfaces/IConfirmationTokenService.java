package com.services.Interfaces;

import java.util.Optional;

import com.entities.ConfirmationToken;

public interface IConfirmationTokenService {
	ConfirmationToken add(ConfirmationToken confirmationToken);
	Optional<ConfirmationToken> getByToken(String token);
	int updateConfirmedAt(String token) ;
    String confirmToken(String token);

}
