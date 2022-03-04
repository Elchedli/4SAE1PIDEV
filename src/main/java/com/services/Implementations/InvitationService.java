package com.services.Implementations;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.Utils.EmailService;
import com.entities.Invitation;
import com.entities.User;
import com.entities.enums.Role;
import com.repositories.InvitationRepository;
import com.services.Interfaces.IInvitationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class InvitationService implements IInvitationService {
	@Autowired
	InvitationRepository invitationRepository;
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;

	@Override
	@Transactional
	public String add(Invitation invitation) {
		String msg = "";
		User companyExists = userService.retrieveByEmail(invitation.getDe());
		User employeeExists = userService.retrieveByEmail(invitation.getPour());
		if (companyExists == null) {
			msg = "company doesn't exist.";
		} else if (employeeExists != null) {
			msg = "employee already exist.";
		} else {
			if (companyExists.getRole() != Role.COMPANY) {
				msg = "this user can't send invitations.";
			} else {
				invitationRepository.save(invitation);
				companyExists.getInvitations().add(invitation);
				String link = "http://localhost:8083/voyageAffaires/registration/addEmployee";
				emailService.sendInvitation(invitation, link);
				msg = "Invitation saved.";
			}
		}
		return msg;
	}

	@Override
	public String update(Invitation invitation) {
		String msg = "";
		boolean ExistsBySujet = invitationRepository.existsBySujet(invitation.getSujet());
		if (ExistsBySujet) {
			invitationRepository.updateInvitation(invitation.getMessage(), invitation.getSujet());
			msg = "invitation updated";
		} else
			msg = "invitation not found";
		return msg;
	}

	@Override
	public String delete(Invitation invitation) {
		String msg = "";
		boolean ExistsBySujet = invitationRepository.existsBySujet(invitation.getSujet());
		if (ExistsBySujet) {
			invitationRepository.delete(invitation);
			msg = "invitation deleted";
		} else
			msg = "invitation not found";
		return msg;
	}

	@Override
	public Invitation retrieveBySujet(String sujet) {
		Boolean ExistsBySujet = invitationRepository.existsBySujet(sujet);
		if (ExistsBySujet)
			return invitationRepository.findInvitationBySujet(sujet);
		else
			return null;
	}

	@Override
	public List<Invitation> retrieveAll() {
		return (List<Invitation>) invitationRepository.findAll();
	}

}
