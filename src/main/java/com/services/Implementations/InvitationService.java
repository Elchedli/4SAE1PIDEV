package com.services.Implementations;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.entities.Invitation;
import com.entities.User;
import com.enums.Role;
import com.repositories.InvitationRepository;
import com.repositories.UserRepository;
import com.services.Interfaces.IInvitationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class InvitationService implements IInvitationService{
	@Autowired
	InvitationRepository invitationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JavaMailSender mailSender;

	@Override
	public String addInvitation(Invitation invitation) {
		String msg = "";
		User company = userRepository.findUserByEmail(invitation.getDe());
		User employee = userRepository.findUserByEmail(invitation.getPour());
		if(company == null){
			msg = "company doesn't exist";
			log.error("company {} doesn't exist.", invitation.getDe());
		} else if(employee == null){
			msg = "employee doesn't exist";
			log.error("employee {} doesn't exist.", invitation.getPour());
		} else {
			if(company.getRole() != Role.COMPANY){
				msg = "this user can't send invitations.";
				log.error("User {} can't send invitations.", invitation.getDe());
			} else if(employee.getRole() != Role.EMPLOYEE){
				msg = "this user can't receive invitations.";
				log.error("User {} can't receive invitations.", invitation.getPour());
			} else {
				invitationRepository.save(invitation);
				
				msg = "Invitation saved.";
				log.error("Invitation {} saved.", invitation.getSujet());
			}
		}
		return msg;
	}
	
	public String envoyerInvitation(String email){
		String link = "http://localhost:8083/voyageAffaires/registration/Employee";
		send(email, buildEmail(email, link));
		return "invitation sent";
	}
	
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Create an Account");
            helper.setFrom("heytravellertn@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email {}", e);
            throw new IllegalStateException("failed to send email");
        }
    }

	@Override
	public String updateInvitation(Invitation invitation) {
		String msg = "";
		Invitation invitationExists = invitationRepository.findInvitationBySujet(invitation.getSujet());
		if(invitationExists == null){
			msg = "invitation not found";
			log.error("Invitation {} not found.", invitation.getSujet());
		} else {
			invitationRepository.updateInvitation(invitation.getMessage(), invitation.getSujet());
			msg = "invitation updated";
			log.error("Invitation {} updated.", invitation.getSujet());
		}
		return msg;
	}

	@Override
	public String deleteInvitation(Invitation invitation) {
		String msg = "";
		Invitation invitationExists = invitationRepository.findInvitationBySujet(invitation.getSujet());
		if(invitationExists != null){
			invitationRepository.delete(invitationExists);
			msg = "invitation deleted";
			log.info("Invitation {} deleted.", invitation.getSujet());
		} else {
			msg = "invitation not found";
			log.error("Invitation {} not found.", invitation.getSujet());
		}
		return msg;
	}

	@Override
	public Invitation retrieveInvitationBySujet(String sujet) {
		Invitation invitationExists = invitationRepository.findInvitationBySujet(sujet);
		if (invitationExists != null) {
			log.info("Invitation {}", invitationExists.getSujet());
			return invitationExists;
		} else {
			log.error("Invitation {} not found.", sujet);
			return null;
		}
	}

	@Override
	public List<Invitation> retrieveAllInvitations() {
		return (List<Invitation>) invitationRepository.findAll();
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Create an account</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> You have been invited by your company to join our application. Please click on the below link to sign up: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
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
