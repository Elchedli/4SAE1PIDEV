package pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pi.entity.Ad;
import pi.enums.PubRegion;
import pi.enums.PubType;

@Repository
public interface AdRepository extends CrudRepository<Ad,Integer>{
	@Query("Select ad From Ad ad where ad.pubRegion = :regpub and ad.pubType = :regtype")
	List<Ad> listerPubRegion(@Param("regpub") PubRegion regpub,@Param("regtype") PubType regtype);
//	List<DataCenter> findByespaceLibreDisqueGreaterThan(int total);
//	@Query("Select c from Client c join c.clboutiques bs where bs.id = :idboutique")
//	List<Client> listerClients(@Param("idboutique") long idboutique);
//	@Query("Select c FROM Client c join c.clboutiques bs where bs.categorie = :category")
//	List<Client> clientsCategory(@Param("category") Categorie categorie);
//	@Query("Select COUNT(*) FROM Client c where c.genre = :genre")
//	int nbreByGenre(@Param("genre") Genre genre);
}
