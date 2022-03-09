package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entities.Tag;


@Repository
public interface TagRepository extends CrudRepository<Tag,String> {
	/*@Query("select t from Tag t")
	List<Tag> getAllTags();*/
	
}
