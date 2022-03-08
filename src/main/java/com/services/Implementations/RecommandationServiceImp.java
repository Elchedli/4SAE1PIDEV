package com.services.Implementations;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Recommandation;
import com.entities.enums.RecommandationAvis;
import com.entities.enums.RecommandationCategory;
import com.repositories.RecommandationRepository;
import com.services.Interfaces.RecommandationService;
@Service
public class RecommandationServiceImp implements RecommandationService {
	
	@Autowired
	RecommandationRepository RepoRecom;
	
	@Override
	public boolean AddRecom(Recommandation rec) {
		RepoRecom.save(rec);
		return true;
	}

	@Override
	public boolean DeleteRecom(int idrecommandaiton) {
		Recommandation recom = RepoRecom.findById(idrecommandaiton).orElse(null);
		RepoRecom.delete(recom);
		return true;
	}

	@Override
	public List<Recommandation> listRecommandation(RecommandationCategory RecCat) {
		return RepoRecom.listerRecomFilter(RecCat);
	}

	@Override
	public List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat,RecommandationAvis recAvis,String country) {
		if(country == "") return RepoRecom.listerRecomFilterAvis(RecCat,recAvis);
		return RepoRecom.listerRecomFilterPays(RecCat,recAvis,country);
	}


	
}
