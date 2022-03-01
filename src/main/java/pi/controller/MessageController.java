package pi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.entity.Discussion;
import pi.entity.Profile;
import pi.service.DiscussionService;
import pi.service.MessaService;

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
	@PostMapping("addDiscussion/{username}")
	public void addDiscussion(@RequestBody Discussion disc,@PathVariable("username") String username) {
		ServiceDiscussion.AddDiscussion(disc,username);
	}
	@GetMapping("ListeDiscussion/{username}")
	public List<Discussion> listDiscussion(@PathVariable("username") String username) {
		return ServiceDiscussion.ListeDiscussion(username);
	}
	
	@GetMapping("SupprimerDiscussion/{refdisc}")
	public void SupprimerDiscussion(@PathVariable("refdisc") String refdisc) {
		ServiceDiscussion.SupprimerDiscussion(refdisc);
	}
	
	@PostMapping("sendMessage")
	public void ajouterEtudiant(@RequestBody Discussion disc,@RequestBody Map<String,String> json) {
		String sender = json.get("sender");
		String messagecontent = json.get("message");
		ServiceMessage.SendMessage(disc, sender, messagecontent);
	}
}
