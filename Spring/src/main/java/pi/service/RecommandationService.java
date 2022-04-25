package com.service;

import java.util.List;

import com.entity.Recommandation;
import com.enums.RecommandationAvis;
import com.enums.RecommandationCategory;

public interface RecommandationService {
	boolean AddRecom(Recommandation rec);
	boolean DeleteRecom(int idrecommandaiton);
	List<Recommandation> listRecommandation(RecommandationCategory RecCat);
	List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat,RecommandationAvis recAvis,String country);
}
