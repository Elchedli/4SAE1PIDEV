package com.repositories;


import org.springframework.stereotype.Repository;

import com.entities.Voyage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface IVoyageRepository extends JpaRepository<Voyage, Integer>{
	@Query(value="SELECT * FROM voyage v WHERE v.destination=:destination",nativeQuery = true)
    List<Voyage> findbydestination(@Param("destination")String destination);

}
