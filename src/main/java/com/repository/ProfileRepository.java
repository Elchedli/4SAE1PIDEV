package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile,Integer>{
	Profile findByUsername(String username);
	@Query(
			value = "select * from profile p",
			nativeQuery = true
	)
	List<Profile> listerPeople();
	@Query(
			value = "select * from profile",
			nativeQuery = true
	)
	List<Profile> listerPeopleInverse();
}
