package pi.implementation;

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
	public boolean AddDiscussion(Discussion disc,String username) {
		System.out.println("username : "+username);
		Profile user = RepoProfile.findByUsername(username);
		System.out.println("user est : "+user);
		user.getDiscPartners().add(disc);
		System.out.println("yahooooo");
		RepoProfile.save(user);
		return true;
	}

	@Override
	public boolean SupprimerDiscussion(String refdisc) {
		Discussion disc = RepoDisc.findByRefdisc(refdisc);
		RepoDisc.delete(disc);
		return true;
	}

	@Override
	public List<Profile> FiltrerDiscussion(String username, String nomprenom) {
		// TODO Auto-generated method stub
		
		//cherche par rapport match donc il affiche un chose approximatif recherche avancée
		return null;
	}
	
	
}
