package com.repositories;

import com.entities.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends CrudRepository<Tag,String> {
	/*@Query("select t from Tag t")
	List<Tag> getAllTags();*/
	
}
