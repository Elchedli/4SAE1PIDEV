package com.services.Interfaces;

import java.util.List;

import com.entities.Partner;

public interface PartenaireService {
	boolean AddPartenaire(Partner p);
	boolean DeletePartenaire(String nomPartenaire);
	List<Partner> listPartenaires();
}
