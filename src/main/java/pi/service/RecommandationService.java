package pi.service;

import java.util.List;

import pi.entity.Recommandation;
import pi.enums.Countries;
import pi.enums.RecommandationAvis;
import pi.enums.RecommandationCategory;

public interface RecommandationService {
	boolean AddRecom(Recommandation rec);
	boolean DeleteRecom(int idrecommandaiton);
	List<Recommandation> listRecommandation(RecommandationCategory RecCat,RecommandationAvis recAvis);
	List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat,Countries country,RecommandationAvis recAvis);
}
