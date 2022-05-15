package com.implementation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Partner;
import com.repository.PartnerRepository;
import com.service.PartenaireService;
@Service
public class PartnerServiceImp implements PartenaireService {
	@Autowired
	PartnerRepository RepoPart;
	@Override
	public boolean AddPartenaire(Partner p) {
		RepoPart.save(p);
		return true;
	}

	@Override
	public boolean DeletePartenaire(String nomPartenaire) {
		Partner part = RepoPart.findByNomPart(nomPartenaire);
		RepoPart.delete(part);
		return true;
	}

	@Override
	public List<Partner> listPartenaires() {
		return (List<Partner>) RepoPart.findAll();
	}	
}
