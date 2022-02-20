package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Invitation;
import com.services.Implementations.InvitationService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/invitation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvitationController {
	@Autowired
	InvitationService invitationService;

	@PostMapping("/addInvitation")
	public String addInvitation(@RequestBody Invitation invitation) {
		return invitationService.addInvitation(invitation);
	}

	@PutMapping("/updateInvitation")
	public String updateInvitation(@RequestBody Invitation invitation) {
		return invitationService.updateInvitation(invitation);
	}

	@DeleteMapping("/deleteInvitation")
	public String deleteInvitation(@RequestBody Invitation invitation) {
		return invitationService.deleteInvitation(invitation);
	}

	@GetMapping("/retrieveInvitationBySubject")
	public Invitation retrieveInvitationBySubject(@RequestBody Subject subject) {
		return invitationService.retrieveInvitationBySubject(subject.getSubject());
	}

	@GetMapping("/retrieveAllInvitations")
	public List<Invitation> retrieveAllInvitations() {
		return invitationService.retrieveAllInvitations();
	}
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Subject {
	String subject;
}