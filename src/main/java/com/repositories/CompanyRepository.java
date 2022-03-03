package com.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entities.Company;


@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer>{

	@Query(value = "SELECT p FROM Company p WHERE p.email = ?1", nativeQuery = true)
	public Company findByEmail(String email);
	
	@Query(value = "UPDATE Company p SET p.enabled = true WHERE P.idCompany = ?1", nativeQuery = true)
	@Modifying
	public void enable(Integer id);
	
	@Query(value ="SELECT p FROM Company p WHERE p.verificationCode = ?1", nativeQuery = true)
	public Company findByVerificationCode(String code) ;
		

}

