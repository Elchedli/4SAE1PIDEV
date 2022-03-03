package pi.service;
import java.util.List;

import pi.entity.Discussion;
import pi.entity.Profile;

public interface DiscussionService {
	List<Discussion> AllDiscussions();
	Discussion EnterDiscussion(String refdisc);
	List<Discussion> ListeDiscussion(String username);
	List<Discussion> FiltrerDiscussion(String username,String nomprenom);
	boolean AddProfile(Profile p);
	boolean AddDiscussion(String sender,String receiver);
	
//	List<Messages>                //peut etre j'ai besoin d'afficher la liste des messages aprés un clic
	boolean SupprimerDiscussion(String refdisc);
}
