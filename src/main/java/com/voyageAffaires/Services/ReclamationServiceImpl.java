package com.voyageAffaires.Services;

import com.voyageAffaires.Repositories.ReclamationRepository;
import com.voyageAffaires.Repositories.UserRepository;
import com.voyageAffaires.entities.Reclamation;
import com.voyageAffaires.entities.User;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
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
    public List<Reclamation> getReclamationByUser(Long idUser) {
        return reclamationRepository.getRecBYId(idUser);
    }

    @Override
    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> addReclamationAndAffecterToUser(List<Reclamation> reclamations, Long idUser) {
         List<Reclamation> reclamations1=reclamationRepository.saveAll(reclamations);
        User us=userRepository.findById(idUser).get();
        for (Reclamation r:reclamations1){
            r.setUser(us);
            reclamationRepository.save(r);
        }
        return this.getAllReclamations();
    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation, Long idReclamation) {
        Reclamation rec=this.getReclamationById(idReclamation);
        if(reclamation.getMessage()!=null)rec.setMessage(reclamation.getMessage());
        if(reclamation.getUser()!=null)rec.setUser(reclamation.getUser());
        if(reclamation.getDateReclamation()!=null)rec.setDateReclamation(reclamation.getDateReclamation());
        return reclamationRepository.save(rec);
    }

    @Override
    public List<Reclamation> deleteReclamationByid(Long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
        return this.getAllReclamations();
    }

    @Override
    @Transactional
    public Reclamation deleteReclamationByUser(Long idUser) {
        reclamationRepository.deleteByUser(idUser);
        return this.getReclamationById(idUser);
    }

    @Override
    public Reclamation addImgReclamation(MultipartFile file, Long idRec) throws IOException {
        Reclamation r=reclamationRepository.findById(idRec).get();
        r.setFileName(file.getOriginalFilename());
        r.setFileType(file.getContentType());
        r.setData(file.getBytes());
        return reclamationRepository.save(r);
    }
    @Override
    public List<Reclamation> addImgToReclamationsList(MultipartFile file, List<Long> idRec) throws IOException {
        List<Reclamation> r=reclamationRepository.findAllById(idRec);
        for(Reclamation rec:r) {
            rec.setFileName(file.getOriginalFilename());
            rec.setFileType(file.getContentType());
            rec.setData(file.getBytes());
            reclamationRepository.save(rec);
        }
        return r;
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
