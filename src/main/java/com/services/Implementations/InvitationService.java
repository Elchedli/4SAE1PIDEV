package com.services.Implementations;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Invitation;
import com.entities.Role;
import com.entities.User;
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

	@Override
	public String addInvitation(Invitation invitation) {
		String msg = "";
		User company = userRepository.findUserByEmail(invitation.getFrom());
		User employee = userRepository.findUserByEmail(invitation.getTo());
		if(company == null){
			msg = "company doesn't exist";
			log.error("company {} doesn't exist.", invitation.getFrom());
		} else if(employee == null){
			msg = "employee doesn't exist";
			log.error("employee {} doesn't exist.", invitation.getTo());
		} else {
			if(company.getRole() != Role.COMPANY){
				msg = "this user can't send invitations.";
				log.error("User {} can't send invitations.", invitation.getFrom());
			} else if(employee.getRole() != Role.EMPLOYEE){
				msg = "this user can't receive invitations.";
				log.error("User {} can't receive invitations.", invitation.getTo());
			} else {
				invitationRepository.save(invitation);
				/*company.getInvitations().add(invitation);
				employee.getInvitations().add(invitation);*/
				msg = "Invitation saved.";
				log.error("Invitation {} saved.", invitation.getSubject());
			}
		}
		return msg;
	}

	@Override
	public String updateInvitation(Invitation invitation) {
		String msg = "";
		Invitation invitationExists = invitationRepository.findInvitationBySubject(invitation.getSubject());
		if(invitationExists == null){
			msg = "invitation not found";
			log.error("Invitation {} not found.", invitation.getSubject());
		} else {
			invitationRepository.updateInvitation(invitation.getMessage(), invitation.getSubject());
			msg = "invitation updated";
			log.error("Invitation {} updated.", invitation.getSubject());
		}
		return msg;
	}

	@Override
	public String deleteInvitation(Invitation invitation) {
		String msg = "";
		Invitation invitationExists = invitationRepository.findInvitationBySubject(invitation.getSubject());
		if(invitationExists != null){
			invitationRepository.delete(invitation);
			msg = "invitation deleted";
			log.info("Invitation {} deleted.", invitation.getSubject());
		} else {
			msg = "invitation not found";
			log.error("Invitation {} not found.", invitation.getSubject());
		}
		return msg;
	}

	@Override
	public Invitation retrieveInvitationBySubject(String subject) {
		return invitationRepository.findInvitationBySubject(subject);
	}

	@Override
	public List<Invitation> retrieveAllInvitations() {
		return (List<Invitation>) invitationRepository.findAll();
	}

}
