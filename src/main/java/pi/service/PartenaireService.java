package pi.service;

import java.util.List;

import pi.entity.Partner;

public interface PartenaireService {
	boolean AddPartenaire(Partner p);
	boolean DeletePartenaire(String nomPartenaire);
	List<Partner> listPartenaires();
}
