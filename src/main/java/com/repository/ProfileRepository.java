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
			value = "select * from profile p where p.nom LIKE %:name% AND p.prenom LIKE %:prename%",
			nativeQuery = true
	)
	List<Profile> listerPeople(@Param("name") String nom,@Param("prename") String prenom);
	@Query(
			value = "select * from profile where nom NOT LIKE %:name% AND prenom NOT LIKE %:prename%",
			nativeQuery = true
	)
	List<Profile> listerPeopleInverse(@Param("name") String nom,@Param("prename") String prenom);
}
