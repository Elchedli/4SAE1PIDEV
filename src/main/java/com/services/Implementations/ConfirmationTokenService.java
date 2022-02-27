package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.ConfirmationToken;
import com.repositories.ConfirmationTokenRepository;
import com.services.Interfaces.IConfirmationTokenService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationTokenService implements IConfirmationTokenService{
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	
	@Override
	public void saveConfirmationToken (ConfirmationToken confirmationToken){
		confirmationTokenRepository.save(confirmationToken);
	}
	@Override
	public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
	@Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
