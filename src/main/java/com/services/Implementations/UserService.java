package com.services.Implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Utils.EmailValide;
import com.entities.ConfirmationToken;
import com.entities.User;
import com.entities.enums.Role;
import com.repositories.UserRepository;
import com.services.Interfaces.IUserService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ConfirmationEmailService confirmationEmailService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	JavaMailSender mailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = retrieveByUsername(username);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	@Override
	public String addCompany(User user) {
		String msg = "";
		if (verification(user)) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRole(Role.COMPANY);
			userRepository.save(user);
			msg = "Company saved.";
			log.info("Company {} saved, confirm your email.", user.getUsername());

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(15), user);
			confirmationEmailService.add(confirmationToken);

			String link = "http://localhost:8083/voyageAffaires/user/confirm?token=" + token;
			confirmationEmailService.send(user.getEmail(), buildConfirmationEmail(user.getUsername(), link));
		} else {
			msg = "Invalid informations, Company not saved.";
			log.error("Invalid informations, Company not saved.");
		}
		return msg;
	}
	@Override
	public String addEmployee(User user) {
		String msg = "";
		if (verification(user)) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRole(Role.EMPLOYEE);
			userRepository.save(user);
			msg = "Employee saved.";
			log.info("Employee {} saved, confirm your email.", user.getUsername());

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(15), user);
			confirmationEmailService.add(confirmationToken);

			String link = "http://localhost:8083/voyageAffaires/user/confirm?token=" + token;
			confirmationEmailService.send(user.getEmail(), buildConfirmationEmail(user.getUsername(), link));
		} else {
			msg = "Invalid informations, Employee not saved.";
			log.error("Invalid informations, Employee not saved.");
		}
		return msg;
	}
	

	@Override
	public String update(User user) {
		String msg = "";
		User userExistsU = retrieveByUsername(user.getUsername());
		User userExistsE = retrieveByEmail(user.getEmail());

		if (userExistsE != null) {
			if (userExistsU != null) {
				if (userExistsE == userExistsU) {
					userRepository.updateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
							user.getRole(), user.getEmail());
					msg = "user updated";
					log.info("user updated");
				} else {
					msg = "username is not available";
					log.error("username {} is not available", user.getUsername());
				}
			} else {
				userRepository.updateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
						user.getRole(), user.getEmail());
				msg = "user updated";
				log.info("user updated");
			}
		} else {
			msg = "user not found";
			log.error("user not found");
		}
		return msg;
	}

	public String sendForgetPasswordEmail(User user) {
		User userExistsE = retrieveByEmail(user.getEmail());
		String link = "http://localhost:8083/voyageAffaires/user/updatePassword";
		send(user.getEmail(), buildForgetPasswordEmail(userExistsE.getUsername(), link));

		return "Click on the link on your email to update your password";
	}

	public String updatePassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.updatePassword(user.getPassword(), user.getEmail());
		return "Password updated";
	}

	@Override
	public String delete(User user) {
		String msg = "";
		User userExists = retrieveByUsername(user.getUsername());

		if (userExists != null) {
			userRepository.delete(userExists);
			msg = "user deleted";
			log.info("User {} deleted.", user.getUsername());
		} else {
			msg = "user not found";
			log.error("User {} not found.", user.getUsername());
		}
		return msg;
	}

	@Override
	public User retrieveByUsername(String username) {
		User userExists = userRepository.findUserByUsername(username);
		if (userExists != null) {
			log.info("User {}", userExists.getUsername());
			return userExists;
		} else {
			log.error("User {} not found.", username);
			return null;
		}
	}

	@Override
	public User retrieveByEmail(String email) {
		User userExists = userRepository.findUserByEmail(email);
		if (userExists != null) {
			log.info("User {}", userExists.getEmail());
			return userExists;
		} else {
			log.error("User {} not found.", email);
			return null;
		}
	}

	@Override
	public List<User> retrieveAll() {
		return (List<User>) userRepository.findAll();
	}

	public boolean verification(User user) {
		User userExistsU = retrieveByUsername(user.getUsername());
		User userExistsE = retrieveByEmail(user.getEmail());

		if (userExistsU != null) {
			log.error("User {} already exists, can't be saved.", user.getUsername());
		} else if (userExistsE != null) {
			log.error("User {} already exists, can't be saved.", user.getEmail());
		} else {
			if (EmailValide.verifierEmail(user.getEmail())) {
				log.info("User {} will be saved.", user.getUsername());
				return true;
			} else {
				log.error("No valid email for User {}.", user.getUsername());
			}
		}
		return false;
	}

	private String buildConfirmationEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
				+ "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
				+ "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
				+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
				+ link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
				+ "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}

	private String buildForgetPasswordEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Forget password</span>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n"
				+ "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n"
				+ "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name
				+ ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Click on the below link to update your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
				+ link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
				+ "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}

	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("ForgetPassword");
			helper.setFrom("heytravellertn@gmail.com");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.error("failed to send email {}", e);
			throw new IllegalStateException("failed to send email");
		}
	}
}
