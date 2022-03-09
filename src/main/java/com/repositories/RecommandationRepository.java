package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Recommandation;
import com.entities.enums.RecommandationAvis;
import com.entities.enums.RecommandationCategory;

@Repository
public interface RecommandationRepository extends CrudRepository<Recommandation,Integer>{
	@Query("Select rec From Recommandation rec where rec.recomCategory = :reccat")
	List<Recommandation> listerRecomFilter(@Param("reccat") RecommandationCategory reccat);
	@Query("Select rec From Recommandation rec where rec.recomCategory = :reccat AND rec.recomAvis = :recavis")
	List<Recommandation> listerRecomFilterAvis(@Param("reccat") RecommandationCategory reccat,@Param("recavis") RecommandationAvis recavis);
	@Query("Select rec From Recommandation rec where rec.recomCategory = :reccat AND rec.recomAvis = :recavis AND rec.country = :patrie")
	List<Recommandation> listerRecomFilterPays(@Param("reccat") RecommandationCategory reccat,@Param("recavis") RecommandationAvis recavis,@Param("patrie") String patrie);
}
