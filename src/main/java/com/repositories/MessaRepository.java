package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entities.Messa;

@Repository
public interface MessaRepository extends CrudRepository<Messa,Integer>{
}
