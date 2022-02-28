package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entities.ConfirmationToken;
import com.repositories.ConfirmationTokenRepository;
import com.repositories.UserRepository;
import com.services.Interfaces.IConfirmationTokenService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ConfirmationTokenService implements IConfirmationTokenService{
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	UserRepository userRepository;
    @Autowired
    JavaMailSender mailSender;
    
    
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

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("yassinekaroui1999@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email {}", e);
            throw new IllegalStateException("failed to send email");
        }
    }
    
    public int enableUser(String email) {
        return userRepository.enableAppUser(email);
    }
    
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = 
                getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }
    
    
}
