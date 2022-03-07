package com.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Discussion;
import com.entity.Profile;
import com.enums.LastSender;
import com.repository.DiscussionRepository;
import com.repository.ProfileRepository;
import com.service.DiscussionService;
import com.utils.TimeUtils;

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
	public Discussion EnterDiscussion(String refdisc,String user) {
		String[] userdefiner = refdisc.split("_");
		System.out.println("user is : "+userdefiner[0]+" et "+userdefiner[1]);
		Discussion discUser = RepoDisc.findById(refdisc).orElse(null);
		if(discUser == null){
			discUser = RepoDisc.findById(userdefiner[1]+"_"+userdefiner[0]).orElse(null);
			String swapper = userdefiner[0];
			userdefiner[0] = userdefiner[1];
			userdefiner[1] = swapper;
		}
		LastSender lastsender = discUser.getLastSender();
		System.out.println("user is : "+lastsender);
		boolean check1 = (lastsender.equals(LastSender.Destinataire) && user.compareTo(userdefiner[1]) == 0);
		boolean check2 = (lastsender.equals(LastSender.Source) && user.compareTo(userdefiner[0]) == 0);
		if(check1 || check2) discUser.setVue_disc(true); // pour le moment on affiche par cette
		if (discUser.getMessages() != null) {
			discUser.getMessages().forEach(e -> {
				e.setTimeago(ConversionTime(e.getDatetemps_msg()));
			});
		}
		RepoDisc.save(discUser);
		discUser.setFriend(true);
		return discUser;
	}

	@Override
	public List<Discussion> FiltrerDiscussion(String username, String nomprenom) {
		List<Discussion> discUser = ListeDiscussion(username);
		List<Discussion> discsAll = AllDiscussions();
		List<Discussion> filtered = new ArrayList<Discussion>();
		discUser.forEach(element -> {
			element.setFriend(true);
			filtered.add(element);
			discsAll.remove(element);
		});

		filtered.addAll(discsAll);
		filtered.forEach(element -> {
			if (element.getMessages() != null) {
				element.getMessages().forEach(e -> {
					e.setTimeago(ConversionTime(e.getDatetemps_msg()));
				});
			}
		});
		return filtered;
	}
	
	@Override
	public List<Profile> FiltrerProfiles(String username, String nomprenom) {
		List<Discussion> discUser = ListeDiscussion(username);
		String[] newdata = nomprenom.split(" ");
//		List<Profile> profileSearch = (List<Profile>) RepoProfile.findAll();
		List<Profile> profileSearch = (List<Profile>) RepoProfile.listerPeople(newdata[0], newdata[1]);
//		List<Profile> profileSearchInverse = RepoProfile.listerPeopleInverse(newdata[0], newdata[1]);
		List<Profile> filtered = new ArrayList<>();
		discUser.forEach(element -> {
			Profile prof = element.getConversation().get(0).getUsername().compareTo(username) != 0 ? element.getConversation().get(0) : element.getConversation().get(1);
			prof.setFriend(true);
			filtered.add(prof);
			if(profileSearch.contains(prof)) profileSearch.remove(prof);
//			if(profileSearchInverse.contains(prof)) profileSearchInverse.remove(prof);
		});
		filtered.addAll(profileSearch);
//		filtered.addAll(profileSearchInverse);
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
		Profile senderprofile = RepoProfile.findByUsername(sender);
		Profile receiverprofile = RepoProfile.findByUsername(receiver);
		boolean discussionnew = (sender.compareTo(receiver) != 0) && (test1 == null && test2 == null) && (senderprofile != null && receiverprofile != null);
		if (discussionnew) {
			Discussion disc = new Discussion();
			List<Profile> profils = new ArrayList<Profile>();
			profils.add(senderprofile);
			profils.add(receiverprofile);
			disc.setConversation(profils);
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
//		System.out.println("date is : " + relativeDate);
		return relativeDate;
	}

}
