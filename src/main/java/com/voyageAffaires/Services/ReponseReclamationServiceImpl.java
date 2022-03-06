package com.voyageAffaires.Services;

import com.voyageAffaires.Repositories.ReponseReclamationRepository;
import com.voyageAffaires.entities.Reclamation;
import com.voyageAffaires.entities.ReponseReclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReponseReclamationServiceImpl implements ReponseReclamationService{
    @Autowired
    private ReponseReclamationRepository rponsereclamationRepository;

    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private EmailService emailService;


    @Override
    public ReponseReclamation getReponseReclamationById(Long id) {
        return rponsereclamationRepository.findById(id).get();
    }

    @Override
    public List<ReponseReclamation> getAllReponseReclamations() {
        return rponsereclamationRepository.findAll();
    }

    @Override
    public List<ReponseReclamation> addReponseReclamationandAffectToReclamation(List<ReponseReclamation> reponseReclamations, Long idReclamation) {
        List<ReponseReclamation> reclamationsRep=rponsereclamationRepository.saveAll(reponseReclamations);
        Reclamation reclamation=reclamationService.getReclamationById(idReclamation);
        for (ReponseReclamation reponseReclamation:reclamationsRep){
            reponseReclamation.setReclamation(reclamation);
            rponsereclamationRepository.save(reponseReclamation);
        }
        emailService.send(reclamation.getUser().getEmail(),"Reponse reclamations","GoodMorning, <b>"+reclamation.getUser().getUsername()+"</b><br /> " +
                "All your reponses has been registred successfuly, thanks !! <br /> cordially");

        return this.getAllReponseReclamations();
    }

    @Override
    public ReponseReclamation updateReponseReclamation(ReponseReclamation reclamation, Long respRecId) {
        ReponseReclamation reponseReclamation=rponsereclamationRepository.findById(respRecId).get();
        if(reclamation.getReclamation()!=null) reponseReclamation.setReclamation(reclamation.getReclamation());
        if(reclamation.getReponse()!=null) reponseReclamation.setReponse(reclamation.getReponse());
        return rponsereclamationRepository.save(reponseReclamation);
    }

    @Override
    public void deleteReclamationById(Long idRespReclamation) {
        rponsereclamationRepository.deleteById(idRespReclamation);
    }

    @Override
    public void deleteAllReclamation() {
        rponsereclamationRepository.deleteAll();
    }
}
