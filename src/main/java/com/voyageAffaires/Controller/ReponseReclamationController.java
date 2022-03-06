package com.voyageAffaires.Controller;


import com.voyageAffaires.Services.ReponseReclamationService;
import com.voyageAffaires.entities.ReponseReclamation;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("ReponseReclamation")
public class ReponseReclamationController {
    @Autowired
    private ReponseReclamationService reclamationService;


    @GetMapping("/all")
    public List<ReponseReclamation> getAllReponseReclamations(){
       return reclamationService.getAllReponseReclamations();
    }

    @GetMapping("/{id}")
    public ReponseReclamation getReponseReclamationById(@PathVariable Long id){
        return reclamationService.getReponseReclamationById(id);
    }

    @PostMapping("/add/{idReclamation}")
    @ApiOperation(value = "add Reponse Reclamation and Affect To Reclamation")
    public ResponseEntity<List<ReponseReclamation>> addReponseReclamationandAffectToReclamation(@PathVariable Long idReclamation,@RequestBody List<ReponseReclamation> reponseReclamations)throws Exception{
        for(ReponseReclamation rp:reponseReclamations){
            if(rp.getReponse().isEmpty()){
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Reponse must be not empty");
            }
        }
        return new ResponseEntity<>(reclamationService.addReponseReclamationandAffectToReclamation(reponseReclamations,idReclamation),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReponseReclamation> updateReponseReclamation(@PathVariable Long id, @RequestBody ReponseReclamation reclamation) throws Exception{

        return new ResponseEntity<>(reclamationService.updateReponseReclamation(reclamation,id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public List<ReponseReclamation> deleteReclamationById(@PathVariable Long id){
        reclamationService.deleteReclamationById(id);
        return this.getAllReponseReclamations();
    }
    @DeleteMapping("/all")
    public String deleteReclamations(){
        reclamationService.deleteAllReclamation();
        return "all reponse reclamations deleted !!";
    }
}
