package com.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Role;
import com.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findUserByUsername(String username);
	User findUserByEmail(String email);
	@Modifying
	@Query("update User u set u.username =:username, u.password =:password where u.email =:email")
	User updateUser(@Param("username") String username, @Param("email") String email,
			@Param("password") String password);
	@Query(value = "SELECT role FROM User u WHERE u.username= :username", nativeQuery = true)
	Role findRoleUserByUsername(@Param("username") String username);

	

}