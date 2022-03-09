package com.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

	@Query(value = "SELECT * FROM Profile  WHERE email = ?1", nativeQuery = true)
	public Profile findByEmail(String email);

	@Modifying
	@Query(value = "UPDATE Profile SET enabled = true WHERE idProfile = ?1", nativeQuery = true)
	public void enable(Long id);

	@Query(value = "SELECT * FROM Profile  WHERE verification_Code = ?1", nativeQuery = true)
	public Profile findByVerificationCode(String code);

	@Query(value = "SELECT * FROM Profile WHERE delete_Code = ?1", nativeQuery = true)
	public Profile findByDeleteCode(String deleteCode);

	@Query(value = "select * from Profile where (prenom like CONCAT('%',:value,'%')) or (nom like CONCAT('%',:value,'%')) or  (email like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> search(@Param("value") String value);

	@Query(value = "select * from Profile   where " + " (city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> searchByCity(@Param("value") String value);

	@Query(value = "select * from Profile   where "
			+ " (country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> searchByCountry(@Param("value") String value);

	@Query(value = "select count(id_profile) from Profile   "
			+ "where ( created_time BETWEEN :date1 and :date2 ) ", nativeQuery = true)
	public int countProfileByDate(@Param("date1") Date date1, @Param("date2") Date date2);

	@Query(value = "select count(id_profile) from Profile  where "
			+ " (country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public int countProfileByCountry(@Param("value") String value);

	@Query(value = "select count(id_profile) from Profile where  (city like CONCAT('%',:value,'%'))", nativeQuery = true)
	public int countProfileByCity(@Param("value") String value);

	Profile findByUsername(String username);

	@Query(value = "select * from profile p where p.nom LIKE %:name% AND p.prenom LIKE %:prename%", nativeQuery = true)
	List<Profile> listerPeople(@Param("name") String nom, @Param("prename") String prenom);

	@Query(value = "select * from profile where nom NOT LIKE %:name% AND prenom NOT LIKE %:prename%", nativeQuery = true)
	List<Profile> listerPeopleInverse(@Param("name") String nom, @Param("prename") String prenom);


}