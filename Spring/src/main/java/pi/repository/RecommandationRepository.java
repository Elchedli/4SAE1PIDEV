package pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pi.entity.Recommandation;
import pi.enums.Countries;
import pi.enums.RecommandationAvis;
import pi.enums.RecommandationCategory;

@Repository
public interface RecommandationRepository extends CrudRepository<Recommandation,Integer>{
	@Query("Select rec From Recommandation rec where rec.recomCategory = :reccat AND rec.recomAvis = :recavis")
	List<Recommandation> listerRecomFilter(@Param("reccat") RecommandationCategory reccat,@Param("recavis") RecommandationAvis recavis);
	@Query("Select rec From Recommandation rec where rec.recomCategory = :reccat AND rec.recomAvis = :recavis AND rec.country = :patrie")
	List<Recommandation> listerRecomFilterAvis(@Param("reccat") RecommandationCategory reccat,@Param("recavis") RecommandationAvis recavis,@Param("patrie") Countries patrie);
}
