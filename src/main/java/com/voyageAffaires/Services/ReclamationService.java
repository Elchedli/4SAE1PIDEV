package com.voyageAffaires.Services;

import com.voyageAffaires.entities.Reclamation;

import java.util.List;

public interface ReclamationService {

    Reclamation getReclamationById(Long id);

    List<Reclamation> getAllReclamations();

    Reclamation getReclamationByUser(Long idUser);

    Reclamation addReclamation(Reclamation reclamation);

    Reclamation updateReclamation(Reclamation reclamation,Long idReclamation);

    List<Reclamation> deleteReclamationByid(Long idReclamation);

    Reclamation deleteReclamationByUser(Long idUser);

    void deleteAllReclamations();

    int nbrReclamationByUser(Long idUser);

    public List<Reclamation> affecterUserToReclamation(Long idReclamation,Long idUser);






}
