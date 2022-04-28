package com.services.Interfaces;

import java.util.List;

import com.entities.Invitation;

public interface IInvitationService {
	String add(Invitation invitation);

	String update(Invitation invitation);

	String delete(Invitation invitation);

	Invitation retrieveBySujet(String sujet);

	List<Invitation> retrieveAll();
}
