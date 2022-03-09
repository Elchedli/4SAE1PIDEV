package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entities.Discussion;

@Repository
public interface DiscussionRepository extends CrudRepository<Discussion,String>{

}
