package com.service;

import java.util.List;

import com.entity.Recommandation;
import com.enums.RecommandationCategory;

public interface RecommandationService {
	boolean AddRecom(Recommandation rec);
	Recommandation getRecom(int id);
	boolean DeleteRecom(int idrecommandaiton);
	Recommandation SearchRecommandation(int idrecom);
	List<Recommandation> listRecommandation(RecommandationCategory RecCat);
	List<Recommandation> listRecommandationAvis(RecommandationCategory RecCat,String country);
}
