package com.services.Interfaces;
import java.util.List;

import com.entities.Discussion;
import com.entities.Profile;

public interface DiscussionService {
	List<Discussion> AllDiscussions();
	Discussion EnterDiscussion(String refdisc,String user);
	List<Discussion> ListeDiscussion(String username);
	List<Discussion> FiltrerDiscussion(String username,String nomprenom);
	boolean AddProfile(Profile p);
	boolean AddDiscussion(String sender,String receiver);
	List<Profile> FiltrerProfiles(String username, String nomprenom);
//	List<Messages>                //peut etre j'ai besoin d'afficher la liste des messages aprés un clic
	boolean SupprimerDiscussion(String refdisc);
}
