package com.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer>{

	@Query(value = "SELECT p FROM Profile p WHERE p.email = ?1", nativeQuery = true)
	public Profile findByEmail(String email);
	
	@Query(value = "UPDATE Profile p SET p.enabled = true WHERE P.idProfile = ?1", nativeQuery = true)
	@Modifying
	public void enable(Integer id);
	
	@Query(value ="SELECT p FROM Profile p WHERE p.verificationCode = ?1", nativeQuery = true)
	public Profile findByVerificationCode(String code) ;
	@Query(value ="SELECT p FROM Profile p WHERE p.deleteCode = ?1", nativeQuery = true)
	public Profile findByDeleteCode(String deleteCode);
		
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