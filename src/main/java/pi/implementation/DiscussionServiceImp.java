package pi.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Discussion;
import pi.entity.Profile;
import pi.repository.DiscussionRepository;
import pi.repository.ProfileRepository;
import pi.service.DiscussionService;

@Service
public class DiscussionServiceImp implements DiscussionService {

	@Autowired
	DiscussionRepository RepoDisc;
	@Autowired
	ProfileRepository RepoProfile;

	public List<Discussion> AllDiscussions() {
		return (List<Discussion>) RepoDisc.findAll();
	}
	@Override
	public List<Discussion> ListeDiscussion(String username) {
		Profile user = RepoProfile.findByUsername(username);
		return user.getDiscPartners();
	}
	
	@Override
	public boolean AddProfile(Profile p) {
		System.out.println("profile : "+p);
		RepoProfile.save(p);
		return true;
	}
	
	@Override
	public boolean AddDiscussion(String sender,String receiver) {
		Discussion disc = new Discussion();
		Profile senderprofile = RepoProfile.findByUsername(sender);
		Profile receiverprofile = RepoProfile.findByUsername(receiver);
		System.out.println("sender profile : "+senderprofile);
		System.out.println("receiver profile : "+receiverprofile);
		if(disc.getConversation()!=null)
			disc.getConversation().add(senderprofile);
		else{
			List<Profile> profils = new ArrayList<Profile>();
			profils.add(senderprofile);
			profils.add(receiverprofile);
			disc.setConversation(profils);
		}
		disc.setRefdisc(sender+"_"+receiver);
		RepoDisc.save(disc);
		return true;
	}

	@Override
	public boolean SupprimerDiscussion(String refdisc) {
		Discussion disc = RepoDisc.findById(refdisc).orElse(null);
		RepoDisc.delete(disc);
		return true;
	}

	@Override
	public List<Profile> FiltrerDiscussion(String username, String nomprenom) {
//		List<Discussion> discUser = ListeDiscussion(username);
//		List<Discussion> discsAll = AllDiscussions();
//		String[] parts = nomprenom.split(" ");
		return null;
	}
	
	
}
