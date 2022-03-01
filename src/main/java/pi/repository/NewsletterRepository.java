package pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pi.entity.Newsletter;
import pi.enums.ArticleRegion;
import pi.enums.Countries;

@Repository
public interface NewsletterRepository extends CrudRepository<Newsletter,Integer>{
	@Query("Select news From Newsletter news where news.articleRegion = :newreg AND news.country = :countries")
	List<Newsletter> listerNewsRegions(@Param("newreg") ArticleRegion regpub,@Param("countries") Countries country);
	@Query("Select news From Newsletter news ORDER BY news.vues DESC")
	List<Newsletter> listerNewsViews();
//	@Query("Select dc From DataCenter dc where dc.dateFabriquation > '2019-11-01'")
//	List<DataCenter> listerDataCenter();
//	List<DataCenter> findByespaceLibreDisqueGreaterThan(int total);
//	@Query("Select c from Client c join c.clboutiques bs where bs.id = :idboutique")
//	List<Client> listerClients(@Param("idboutique") long idboutique);
//	@Query("Select c FROM Client c join c.clboutiques bs where bs.categorie = :category")
//	List<Client> clientsCategory(@Param("category") Categorie categorie);
//	@Query("Select COUNT(*) FROM Client c where c.genre = :genre")
//	int nbreByGenre(@Param("genre") Genre genre);
}
