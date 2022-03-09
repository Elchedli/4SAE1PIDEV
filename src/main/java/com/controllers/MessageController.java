package com.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Discussion;
import com.entities.Profile;
import com.services.Interfaces.DiscussionService;
import com.services.Interfaces.MessaService;

@RestController
@RequestMapping("/message/")
public class MessageController {

	@Autowired
	DiscussionService ServiceDiscussion;
	@Autowired
	MessaService ServiceMessage;
	
	@PostMapping("addProfile")
	public void addProfile(@RequestBody Profile p) {
		ServiceDiscussion.AddProfile(p);
	}
	@PostMapping("addDiscussion")
	public boolean addDiscussion(@RequestBody Map<String,String> json) {
		String sender = json.get("sender");
		String receiver = json.get("receiver");
		return ServiceDiscussion.AddDiscussion(sender,receiver);
	}
	
	@PostMapping("AllDiscussion")
	public List<Discussion> AllDiscussion() {
		return ServiceDiscussion.AllDiscussions();
	}
	
	@PostMapping("EnterDiscussion")
	public Discussion EnterDiscussion(@RequestBody Map<String,String> json) {
		String refdisc = json.get("refdisc");
		String user = json.get("user");
		return ServiceDiscussion.EnterDiscussion(refdisc,user);
	}
	
	@GetMapping("ListeDiscussion/{username}")
	public List<Discussion> listDiscussion(@PathVariable("username") String username) {
		return ServiceDiscussion.ListeDiscussion(username);
	}
	
	@PostMapping("FiltrerDiscussion")
	public List<Discussion> FilterDiscussion(@RequestBody Map<String,String> json) {
		String username = json.get("username");
		String nomprenom = json.get("nomprenom");
		return ServiceDiscussion.FiltrerDiscussion(username, nomprenom);
	}
	
	@PostMapping("FiltrerProfiles")
	public List<Profile> FilterProfiles(@RequestBody Map<String,String> json) {
		String username = json.get("username");
		String nomprenom = json.get("nomprenom");
		return ServiceDiscussion.FiltrerProfiles(username, nomprenom);
	}
	
	
	@GetMapping("SupprimerDiscussion/{refdisc}")
	public void SupprimerDiscussion(@PathVariable("refdisc") String refdisc) {
		ServiceDiscussion.SupprimerDiscussion(refdisc);
	}
	
	@PostMapping("sendMessage")
	public String ajouterEtudiant(@RequestBody Map<String,String> json) {
		String refdisc = json.get("refdisc");
		String sender = json.get("sender");
		String messagecontent = json.get("message");
		return ServiceMessage.SendMessage(refdisc, sender, messagecontent);
	}
}
