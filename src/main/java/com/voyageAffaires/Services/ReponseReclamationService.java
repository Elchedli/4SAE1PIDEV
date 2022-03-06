package com.voyageAffaires.Services;

import com.voyageAffaires.entities.Reclamation;
import com.voyageAffaires.entities.ReponseReclamation;

import java.util.List;

public interface ReponseReclamationService {
    ReponseReclamation getReponseReclamationById(Long id);
    List<ReponseReclamation> getAllReponseReclamations();
    List<ReponseReclamation> addReponseReclamationandAffectToReclamation(List<ReponseReclamation> reponseReclamations,Long idReclamation );
    ReponseReclamation updateReponseReclamation(ReponseReclamation reclamation,Long respRecId);
    void deleteReclamationById(Long idRespReclamation);
    void deleteAllReclamation();
}
