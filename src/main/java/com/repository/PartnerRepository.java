package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Partner;

@Repository
public interface PartnerRepository extends CrudRepository<Partner,Integer>{
	Partner findByNomPart(String nomPart);
}
