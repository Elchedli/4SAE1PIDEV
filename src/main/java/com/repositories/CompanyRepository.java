package com.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Company;


@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer>{

	@Query(value = "SELECT * FROM Company WHERE email = ?1", nativeQuery = true)
	public Company findByEmail(String email);
	
	@Modifying
	@Query(value = "UPDATE Company p SET p.enabled = true WHERE p.idCompany = ?1", nativeQuery = true)
	public void enable(Integer id);
	
	@Query(value ="SELECT * FROM Company  WHERE verification_code = ?1", nativeQuery = true)
	public Company findByVerificationCode(String code) ;
		
	@Query(value = "select c from Company c where c.nameCompany like CONCAT('%',:value,'%')) or (c.contactPerson like CONCAT('%',:value,'%')) or  (c.email like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> search(@Param("value") String value);
	
	@Query(value = "select p.* from Company p   where "
			+ " (p.city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> searchByCity(@Param("value") String value);

	@Query(value = "select p.* from Company p   where "
			+ " (p.country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> searchByCountry(@Param("value") String value);
	

	@Query(value = "select count(p.idCompany) from Company p  "
			+ "where ( p.created_time BETWEEN :date1 and :date2 ) ", nativeQuery = true)
	public int countCompanyByDate( @Param("date1") Date date1, @Param("date2") Date date2);

	@Query(value = "select count(p.idCompany) from Company p   where "
			+ " (p.country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> countCompanyByCountry(@Param("value") String value);
	
	@Query(value = "select count(p.idCompany) from Company p   where "
			+ " (p.city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> countCompanyByCity(@Param("value") String value);
	
}

