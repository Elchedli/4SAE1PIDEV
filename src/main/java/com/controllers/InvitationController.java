package com.controllers;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Utils.FileUpload;
import com.entities.Invitation;
import com.entities.User;
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

	@PostMapping("/add")
	@Transactional
	public String addInvitation(@Valid @RequestPart Invitation invitation,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		invitation.setImage(fileName);
		String uploadDir = "invitation-photos/" + invitation.getSujet();
		FileUpload.saveFile(uploadDir, fileName, multipartFile);
		return invitationService.add(invitation);
	}

	@PutMapping("/update")
	public String updateInvitation(@RequestPart Invitation invitation) {
		return invitationService.update(invitation);
	}

	@DeleteMapping("/deleteInvitation")
	public String deleteInvitation(@RequestBody Invitation invitation) {
		return invitationService.delete(invitation);
	}

	@GetMapping("/retrieveBySujet")
	public Invitation retrieveBySujet(@RequestBody Subject subject) {
		return invitationService.retrieveBySujet(subject.getSujet());
	}

	@GetMapping("/retrieveAll")
	public List<Invitation> retrieveAll() {
		return invitationService.retrieveAll();
	}

	@PutMapping("/activate")
	public void activateInvitation(@RequestBody Subject subject) {
		invitationService.activateInvitation(subject.getSujet());
		
	}
	
	@GetMapping("/retrieveByUser")
	public List<Invitation> retrieveByUser(@RequestBody User user) {
		return invitationService.retrieveByUser(user.getEmail());
	}
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Subject {
	String sujet;
}