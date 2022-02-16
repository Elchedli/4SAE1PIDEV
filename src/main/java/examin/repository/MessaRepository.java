package examin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import examin.entity.Messa;

@Repository
public interface MessaRepository extends CrudRepository<Messa,Integer>{
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
