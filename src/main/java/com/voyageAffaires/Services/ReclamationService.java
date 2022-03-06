package com.voyageAffaires.Services;

import com.voyageAffaires.entities.Reclamation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReclamationService {

    Reclamation getReclamationById(Long id);

    List<Reclamation> getAllReclamations();

    List<Reclamation> getReclamationByUser(Long idUser);

    Reclamation addReclamation(Reclamation reclamation);
    List<Reclamation> addReclamationAndAffecterToUser(List<Reclamation> reclamations, Long idUser) ;
    Reclamation updateReclamation(Reclamation reclamation,Long idReclamation);

    List<Reclamation> deleteReclamationByid(Long idReclamation);

    Reclamation deleteReclamationByUser(Long idUser);

    Reclamation addImgReclamation(MultipartFile file,Long idRec)throws IOException;

    List<Reclamation> addImgToReclamationsList(MultipartFile file,List<Long> idRec)throws IOException;
    void deleteAllReclamations();

    int nbrReclamationByUser(Long idUser);

    public List<Reclamation> affecterUserToReclamation(Long idReclamation,Long idUser);






}
