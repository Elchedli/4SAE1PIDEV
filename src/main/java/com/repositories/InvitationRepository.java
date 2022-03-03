package com.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Invitation;
@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long>{
	Invitation findInvitationBySujet(String sujet);
	@Modifying
	@Query("update Invitation i set i.message= :message where i.sujet= :sujet")
	void updateInvitation(@Param("message") String message, @Param("sujet") String sujet);
	
	boolean existsBySujet(String sujet);


}
