package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Messa;

@Repository
public interface MessaRepository extends CrudRepository<Messa,Integer>{
}
