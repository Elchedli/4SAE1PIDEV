package pi.implementation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.entity.Recommandation;
import pi.enums.Countries;
import pi.enums.RecommandationAvis;
import pi.enums.RecommandationCategory;
import pi.repository.RecommandationRepository;
import pi.service.RecommandationService;
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
	public List<Recommandation> listRecommandation(RecommandationCategory RecCat, RecommandationAvis recavis) {
		return RepoRecom.listerRecomFilter(RecCat,recavis);
	}

	@Override
	public List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat, Countries country,
			RecommandationAvis recAvis) {
		return RepoRecom.listerRecomFilterAvis(RecCat,recAvis,country);
		// avec IP
	}


	
}
