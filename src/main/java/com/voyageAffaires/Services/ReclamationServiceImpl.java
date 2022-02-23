package com.voyageAffaires.Services;

import com.voyageAffaires.Repositories.ReclamationRepository;
import com.voyageAffaires.Repositories.UserRepository;
import com.voyageAffaires.entities.Reclamation;
import com.voyageAffaires.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationServiceImpl implements ReclamationService{
    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation getReclamationByUser(Long idUser) {
        return reclamationRepository.getReclamationByUser(userRepository.findById(idUser).orElse(null));
    }

    @Override
    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation, Long idReclamation) {
        Reclamation rec=this.getReclamationById(idReclamation);
        if(reclamation.getMessage()!=null)rec.setMessage(reclamation.getMessage());
        if(reclamation.getUser()!=null)rec.setUser(reclamation.getUser());
        return reclamationRepository.save(rec);
    }

    @Override
    public List<Reclamation> deleteReclamationByid(Long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
        return this.getAllReclamations();
    }

    @Override
    public Reclamation deleteReclamationByUser(Long idUser) {
        return reclamationRepository.deleteByUser(userRepository.findById(idUser).orElse(null));
    }

    @Override
    public void deleteAllReclamations() {
         reclamationRepository.deleteAll();
    }

    @Override
    public int nbrReclamationByUser(Long idUser) {
        return reclamationRepository.nbrReclamationByUser(idUser);
    }

    @Override
    public List<Reclamation> affecterUserToReclamation(Long idReclamation, Long idUser) {
        Reclamation rec=this.getReclamationById(idReclamation);
        User us=userRepository.findById(idUser).orElse(null);
        rec.setUser(us);
        reclamationRepository.save(rec);
        return this.getAllReclamations();
    }
}
