package com.repositories;


import org.springframework.stereotype.Repository;

import com.entities.Voyage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface IVoyageRepository extends JpaRepository<Voyage, Integer>{
	List<Voyage> findBySubject(String subject);
	List<Voyage> findByDestination(String destination);
}
