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
public interface CompanyRepository extends CrudRepository<Company, Long>{

	@Query(value = "SELECT * FROM Company WHERE email = ?1", nativeQuery = true)
	public Company findByEmail(String email);
	
	@Modifying
	@Query(value = "UPDATE Company p SET p.enabled = true WHERE p.idCompany = ?1", nativeQuery = true)
	public void enable(Long id);
	
	@Query(value ="SELECT * FROM Company  WHERE verification_code = ?1", nativeQuery = true)
	public Company findByVerificationCode(String code) ;
		
	@Query(value = "select * from Company where (name_company like CONCAT('%',:value,'%')) or (contact_Person like CONCAT('%',:value,'%')) or  (email like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> search(@Param("value") String value);
	
	@Query(value = "select * from Company  where "
			+ " (city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> searchByCity(@Param("value") String value);

	@Query(value = "select * from Company    where "
			+ " (country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public List<Company> searchByCountry(@Param("value") String value);
	

	@Query(value = "select count(id_company) from Company   "
			+ "where ( created_time BETWEEN :date1 and :date2 ) ", nativeQuery = true)
	public int countCompanyByDate( @Param("date1") Date date1, @Param("date2") Date date2);

	@Query(value = "select count(id_company) from Company  where "
			+ " (country like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public int countCompanyByCountry(@Param("value") String value);
	
	@Query(value = "select count(id_company) from Company   where  (city like CONCAT('%',:value,'%')) ", nativeQuery = true)
	public int  countCompanyByCity(@Param("value") String value);
	
}

