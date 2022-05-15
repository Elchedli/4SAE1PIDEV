package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Discussion;

@Repository
public interface DiscussionRepository extends CrudRepository<Discussion,String>{

}
