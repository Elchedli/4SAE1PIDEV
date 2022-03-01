package com.services.Interfaces;

import java.util.Optional;

import com.entities.ConfirmationToken;

public interface IConfirmationEmailService {
	void add (ConfirmationToken confirmationToken);
	Optional<ConfirmationToken> getByToken(String token);
	int setConfirmedAt(String token) ;
    void send(String to, String email);
    int enableUser(String email);
    String confirmToken(String token);

}
