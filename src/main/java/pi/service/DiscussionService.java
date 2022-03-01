package pi.service;
import java.util.List;
import pi.entity.Discussion;
import pi.entity.Profile;

public interface DiscussionService {
	List<Discussion> ListeDiscussion(String username);
	boolean AddProfile(Profile p);
	boolean AddDiscussion(Discussion discussion,String username);
//	List<Messages>                //peut etre j'ai besoin d'afficher la liste des messages aprés un clic
	boolean SupprimerDiscussion(String refdisc);
	List<Profile> FiltrerDiscussion(String username,String nomprenom);
}
