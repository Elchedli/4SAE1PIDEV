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
				//company.getInvitations().add(invitation);
				//employee.getInvitations().add(invitation);
				msg = "Invitation saved.";
				log.error("Invitation {} saved.", invitation.getSujet());
			}
		}
		return msg;
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
			invitationRepository.delete(invitation);
			msg = "invitation deleted";
			log.info("Invitation {} deleted.", invitation.getSujet());
		} else {
			msg = "invitation not found";
			log.error("Invitation {} not found.", invitation.getSujet());
		}
		return msg;
	}

	@Override
	public Invitation retrieveInvitationBySubject(String subject) {
		return invitationRepository.findInvitationBySujet(subject);
	}

	@Override
	public List<Invitation> retrieveAllInvitations() {
		return (List<Invitation>) invitationRepository.findAll();
	}

}
