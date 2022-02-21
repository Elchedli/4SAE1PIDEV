package com.services.Interfaces;

import java.util.List;

import com.entities.Invitation;

public interface IInvitationService {
	String addInvitation(Invitation invitation);
	String updateInvitation(Invitation invitation);
	String deleteInvitation(Invitation invitation);
	Invitation retrieveInvitationBySujet(String sujet);
	List<Invitation> retrieveAllInvitations();
}
