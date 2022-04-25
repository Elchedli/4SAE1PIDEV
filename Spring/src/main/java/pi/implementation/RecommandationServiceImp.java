package com.implementation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Recommandation;
import com.enums.RecommandationAvis;
import com.enums.RecommandationCategory;
import com.repository.RecommandationRepository;
import com.service.RecommandationService;
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
