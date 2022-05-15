package com.service;
import java.util.List;

import com.entity.Discussion;
import com.entity.Profile;

public interface DiscussionService {
	List<Discussion> AllDiscussions();
	Discussion EnterDiscussion(String refdisc,String user);
	List<Discussion> ListeDiscussion(String username);
	List<Discussion> FiltrerDiscussion(String username,String nomprenom);
	boolean AddProfile(Profile p);
	boolean AddDiscussion(String sender,String receiver);
	List<Profile> FiltrerProfiles(String username);
//	List<Messages>                //peut etre j'ai besoin d'afficher la liste des messages aprés un clic
	boolean SupprimerDiscussion(String refdisc);
}
