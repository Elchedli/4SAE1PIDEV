package com.service;

import java.util.List;

import com.entity.Partner;

public interface PartenaireService {
	boolean AddPartenaire(Partner p);
	boolean DeletePartenaire(String nomPartenaire);
	List<Partner> listPartenaires();
}
