package pi.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Discussion;
import pi.entity.Profile;
import pi.repository.DiscussionRepository;
import pi.repository.ProfileRepository;
import pi.service.DiscussionService;
import pi.utils.TimeUtils;

@Service
public class DiscussionServiceImp implements DiscussionService {

	@Autowired
	DiscussionRepository RepoDisc;
	@Autowired
	ProfileRepository RepoProfile;

	@Override
	public List<Discussion> AllDiscussions() {
		return (List<Discussion>) RepoDisc.findAll();
	}

	@Override
	public List<Discussion> ListeDiscussion(String username) {
		Profile user = RepoProfile.findByUsername(username);
		return user.getDiscPartners();
	}

	@Override
	public Discussion EnterDiscussion(String refdisc) {
		Discussion discUser = RepoDisc.findById(refdisc).orElse(null);
		discUser.setVue_disc(true); // pour le moment on affiche par cette
									// fonction
		RepoDisc.save(discUser);
		return discUser;
	}

	@Override
	public List<Discussion> FiltrerDiscussion(String username, String nomprenom) {
		List<Discussion> discUser = ListeDiscussion(username);
		// List<Discussion> discUser = ListeDiscussionNomPrenom(username);
		List<Discussion> discsAll = AllDiscussions();
		List<Discussion> filtered = new ArrayList<Discussion>();

		// for(Discussion f:discUser){
		// sample = (verifDiscussion) f;
		// sample.setFriends(true);
		// filtered.add(sample);
		// }
		discUser.forEach(element -> {
			element.setFriend(true);
			if (element.getMessages() != null) {
				element.getMessages().forEach(e -> {
					e.setTimeago(ConversionTime(e.getDatetemps_msg()));
				});
			}
			filtered.add(element);
			discsAll.remove(element);
		});

		filtered.addAll(discsAll);
		// for(Discussion f:discsAll){
		// verifDiscussion sample = (verifDiscussion) f;
		// sample.setFriends(false);
		// filtered.add(sample);
		// }
		return filtered;
	}

	@Override
	public boolean AddProfile(Profile p) {
		System.out.println("profile : " + p);
		p.setUsername((p.getUsername()).toLowerCase());
		RepoProfile.save(p);
		return true;
	}

	@Override
	public boolean AddDiscussion(String sender, String receiver) {
		Discussion test1 = RepoDisc.findById(sender + "_" + receiver).orElse(null);
		Discussion test2 = RepoDisc.findById(receiver + "_" + sender).orElse(null);
		System.out.println("test 1 : " + test1);
		System.out.println("test 2 : " + test2);
		boolean discussionnew = RepoDisc.findById(sender + "_" + receiver).orElse(null) != null
				|| RepoDisc.findById(sender + "_" + receiver).orElse(null) != null;
		if (!discussionnew) {
			Discussion disc = new Discussion();
			System.out.println("sender is : " + sender + "receiver : " + receiver);
			Profile senderprofile = RepoProfile.findByUsername(sender);
			// System.out.println("sender profile : " +
			// senderprofile.getDiscPartners());
			Profile receiverprofile = RepoProfile.findByUsername(receiver);
			// System.out.println("sender profile : " +
			// senderprofile.getDiscPartners());
			// System.out.println("receiver profile : " + receiverprofile);
			// if(disc.getConversation()!=null)
			// disc.getConversation().add(senderprofile);
			// else{
			List<Profile> profils = new ArrayList<Profile>();
			profils.add(senderprofile);
			profils.add(receiverprofile);
			disc.setConversation(profils);
			// }
			// System.out.println("arriver?");
			disc.setRefdisc(sender + "_" + receiver);
			RepoDisc.save(disc);
			return true;
		}
		return false;
	}

	@Override
	public boolean SupprimerDiscussion(String refdisc) {
		Discussion disc = RepoDisc.findById(refdisc).orElse(null);
		RepoDisc.delete(disc);
		return true;
	}

	public String ConversionTime(Date dateAd) {
		long millis = dateAd.getTime();
		String relativeDate = String.valueOf(TimeUtils.getRelativeTime(millis));
		System.out.println("date is : " + relativeDate);
		return relativeDate;
	}

}
