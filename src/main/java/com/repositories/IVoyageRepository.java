package com.repositories;


import org.springframework.stereotype.Repository;

import com.entities.Voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface IVoyageRepository extends JpaRepository<Voyage, Integer>{
	//@Modifying
	//@Query("update Voyage v set v = :voyage where u.id_voyage =:id")
	//int updateVoyage(@Param("entreprise") Voyage voyage, @Param("id") int id);
}
