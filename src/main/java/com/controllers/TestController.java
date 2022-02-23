package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Invitation;
import com.services.Implementations.InvitationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/test")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestController {
	@Autowired
	InvitationService invitationService;
	@GetMapping("/retrieveAllInvitations")
	public List<Invitation> retrieveAllInvitations() {
		return invitationService.retrieveAllInvitations();
	}
}
