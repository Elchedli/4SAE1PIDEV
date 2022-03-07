package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile,Integer>{
	Profile findByUsername(String username);
	@Query(
			value = "select * from profile p where p.nom LIKE '%:nom%' AND p.prenom LIKE '%:prenom%'",
			nativeQuery = true
	)
	List<Profile> listerPeople(@Param("nom") String nom,@Param("prenom") String prenom);
//	@Query(
//			value = "select * from profile where nom NOT LIKE '%:nom%' AND prenom NOT LIKE '%:prenom%';",
//			nativeQuery = true
//	)
//	List<Profile> listerPeopleInverse(@Param("nom") String nom,@Param("prenom") String prenom);
//	List<DataCenter> findByespaceLibreDisqueGreaterThan(int total);
//	@Query("Select c from Client c join c.clboutiques bs where bs.id = :idboutique")
//	List<Client> listerClients(@Param("idboutique") long idboutique);
//	@Query("Select c FROM Client c join c.clboutiques bs where bs.categorie = :category")
//	List<Client> clientsCategory(@Param("category") Categorie categorie);
//	@Query("Select COUNT(*) FROM Client c where c.genre = :genre")
//	int nbreByGenre(@Param("genre") Genre genre);
}
