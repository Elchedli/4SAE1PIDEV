package com.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.User;
import com.entities.enums.Role;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);

	User findByUsername(String username);
	User findByEmail(String email);
	
	@Modifying
	@Query("update User u set u.username= :username, u.password= :password, u.role= :role where u.email= :email")
	void update(@Param("username") String username, @Param("password") String password, @Param("role") Role role,
			@Param("email") String email);
	
	@Modifying
    @Query("update User u set u.password = :password where u.email= :email")
    int updatePassword(@Param("password") String password,@Param("email") String email);
	
    @Modifying
    @Query("update User u set u.enabled = TRUE where u.email= :email")
    int enableUser(@Param("email") String email);
    
    
}