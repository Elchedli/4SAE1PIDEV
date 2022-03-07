package com.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Profile;
import com.entities.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

	@Query(value = "SELECT * FROM Profile  WHERE email = ?1", nativeQuery = true)
	public Profile findByEmail(String email);

	@Modifying
	@Query(value = "UPDATE Profile SET enabled = true WHERE idProfile = ?1", nativeQuery = true)
	public void enable(Integer id);

	@Query(value = "SELECT * FROM Profile  WHERE verification_Code = ?1", nativeQuery = true)
	public Profile findByVerificationCode(String code);

	@Query(value = "SELECT * FROM Profile WHERE delete_Code = ?1", nativeQuery = true)
	public Profile findByDeleteCode(String deleteCode);

	@Query(value = "select c from Profile c where c.prenom like CONCAT('%',:value,'%')) or (c.nom like CONCAT('%',:value,'%')) or  (c.email like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> search(@Param("value") String value);

	@Query(value = "select p.* from Profile p   where " + " (p.city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> searchByCity(@Param("value") String value);

	@Query(value = "select p.* from Profile p   where "
			+ " (p.country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> searchByCountry(@Param("value") String value);

	@Query(value = "select count(p.idProfile) from Profile p  "
			+ "where ( p.created_time BETWEEN :date1 and :date2 ) ", nativeQuery = true)
	public int countProfileByDate(@Param("date1") Date date1, @Param("date2") Date date2);

	@Query(value = "select count(p.idProfile) from Profile p   where "
			+ " (p.country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> countProfileByCountry(@Param("value") String value);

	@Query(value = "select count(p.idProfile) from Profile p   where "
			+ " (p.city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Profile> countProfileByCity(@Param("value") String value);

}
