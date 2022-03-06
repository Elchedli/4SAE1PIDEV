package com.voyageAffaires.Repositories;

import com.voyageAffaires.entities.ReponseReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseReclamationRepository extends JpaRepository<ReponseReclamation, Long> {
}
