package com.voyageAffaires.Repositories;

import com.voyageAffaires.entities.Reclamation;
import com.voyageAffaires.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Reclamation getReclamationByUser(User user);
    Reclamation deleteByUser(User user);

    @Query("SELECT count(R) FROM  Reclamation R,User U WHERE  U.id=R.user.id and U.id=:id")
    int nbrReclamationByUser(Long id);
}
