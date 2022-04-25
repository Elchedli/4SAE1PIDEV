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
	ProfileRepository RepoProfile;

	@Override
	public List<Discussion> ListeDiscussion(String username) {
		Profile user = RepoProfile.findByusername(username);
		return user.getDiscPartners();
	}

	@Override
	public boolean SupprimerDiscussion(String refdisc) {
		Discussion disc = RepoDisc.findById(refdisc).orElse(null);
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
