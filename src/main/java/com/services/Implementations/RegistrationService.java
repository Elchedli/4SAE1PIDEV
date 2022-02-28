package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Utils.EmailValide;
import com.entities.ConfirmationToken;
import com.entities.User;
import com.enums.Role;
import com.repositories.UserRepository;
import com.services.Interfaces.IRegistrationService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class RegistrationService implements IRegistrationService{
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;	
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public String register(User user) {
		String msg = "";
		User userExistsU = userService.retrieveUserByUsername(user.getUsername());
		User userExistsE = userService.retrieveUserByEmail(user.getEmail());

		if (userExistsU != null) {
			msg = "There is already a user registered with the username provided.";
			log.error("User {} already exists, can't be saved.", user.getUsername());
		} else if (userExistsE != null) {
			msg = "There is already a user registered with the email provided.";
			log.error("User {} already exists, can't be saved..", user.getEmail());
		} else {
			if (EmailValide.verifierEmail(user.getEmail())) {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				user.setRole(Role.COMPANY);
				userRepository.save(user);
				msg = "Company saved.";
				log.info("Company {} saved, confirm your email.", user.getUsername());
				
				String token = UUID.randomUUID().toString();
				ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),user);
				confirmationTokenService.saveConfirmationToken(confirmationToken);
				
				String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
				confirmationTokenService.send(
		                user.getEmail(),
		                buildEmail(user.getUsername(), link));
			} else {
				msg = "this email is not valid, user not saved.";
				log.error("No valid email for User {}.", user.getUsername());
			}
		}
		return msg;
	}
	
	
	public String addAdmin(User admin) {
		String msg = "";
		User adminExistsU = userService.retrieveUserByUsername(admin.getUsername());
		User adminExistsE = userService.retrieveUserByEmail(admin.getEmail());
		if (adminExistsU != null) {
			msg = "There is already an admin registered with the username provided.";
			log.error("Admin {} already exists, can't be saved.", admin.getUsername());
		} else if (adminExistsE != null) {
			msg = "There is already an admin registered with the email provided.";
			log.error("Admin {} already exists, can't be saved.", admin.getEmail());
		} else {
			if (EmailValide.verifierEmail(admin.getEmail())) {
				admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
				msg = "Admin saved.";
				log.info("Admin {} saved, confirm your email.", admin.getUsername());
				
				String token = UUID.randomUUID().toString();
				ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),admin);
				confirmationTokenService.saveConfirmationToken(confirmationToken);
				
				String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
				confirmationTokenService.send(
						admin.getEmail(),
		                buildEmail(admin.getUsername(), link));
			} else {
				msg = "This email is not valid, user not saved.";
				log.error("This email is not valid, Admin {} not saved.", admin.getUsername());
			}
		}
		return msg;
	}
	
	
	public String addEmployee(User employee) {
		String msg = "";
		User adminExistsU = userService.retrieveUserByUsername(employee.getUsername());
		User adminExistsE = userService.retrieveUserByEmail(employee.getEmail());
		if (adminExistsU != null) {
			msg = "There is already an admin registered with the username provided.";
			log.error("Admin {} already exists, can't be saved.", employee.getUsername());
		} else if (adminExistsE != null) {
			msg = "There is already an admin registered with the email provided.";
			log.error("Admin {} already exists, can't be saved.", employee.getEmail());
		} else {
			if (EmailValide.verifierEmail(employee.getEmail())) {
				employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
				employee.setRole(Role.ADMIN);
				userRepository.save(employee);
				msg = "Admin saved.";
				log.info("Admin {} saved, confirm your email.", employee.getUsername());
				
				String token = UUID.randomUUID().toString();
				ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),employee);
				confirmationTokenService.saveConfirmationToken(confirmationToken);
				
				String link = "http://localhost:8083/voyageAffaires/registration/confirm?token=" + token;
				confirmationTokenService.send(
						employee.getEmail(),
		                buildEmail(employee.getUsername(), link));
			} else {
				msg = "This email is not valid, user not saved.";
				log.error("This email is not valid, Admin {} not saved.", employee.getUsername());
			}
		}
		return msg;
	}
	private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
