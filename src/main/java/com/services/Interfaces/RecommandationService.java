package com.services.Interfaces;

import java.util.List;

import com.entities.Recommandation;
import com.entities.enums.RecommandationAvis;
import com.entities.enums.RecommandationCategory;

public interface RecommandationService {
	boolean AddRecom(Recommandation rec);
	boolean DeleteRecom(int idrecommandaiton);
	List<Recommandation> listRecommandation(RecommandationCategory RecCat);
	List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat,RecommandationAvis recAvis,String country);
}
