package com.voyageAffaires.Controller;

import com.voyageAffaires.Services.ReclamationService;
import com.voyageAffaires.entities.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @GetMapping("/all")
    public List<Reclamation> getAllReclamations(){
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable Long id){
        return reclamationService.getReclamationById(id);
    }

    @GetMapping("/user/{idUser}")
    public Reclamation getReclamationByUser(@PathVariable Long idUser){
        return reclamationService.getReclamationByUser(idUser);
    }

    @GetMapping("/user/nbrReclamation/{idUser}")
    public int nbrReclamationByUser(@PathVariable Long idUser){
        return reclamationService.nbrReclamationByUser(idUser);
    }

    @PostMapping("/add")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation){
      return   reclamationService.addReclamation(reclamation);
    }

    @PutMapping("/update/{idReclamation}")
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation,@PathVariable  Long idReclamation){
        return reclamationService.updateReclamation(reclamation,idReclamation);
    }

    @DeleteMapping("/delete")
    public String deleteAllReclamations(){
        reclamationService.deleteAllReclamations();
        return "All deleted !!";
    }

    @DeleteMapping("/delete/{id}")
    public List<Reclamation> deleteReclamationById(@PathVariable Long id){
        reclamationService.deleteReclamationByid(id);
        return this.getAllReclamations();
    }

    @DeleteMapping("/delete/user/{id}")
    public List<Reclamation> deleteReclamationByUser(@PathVariable Long id){
        reclamationService.deleteReclamationByUser(id);
        return this.getAllReclamations();
    }

    @GetMapping("/affectUser/{idRec}/{idUser}")
    public List<Reclamation> affecterUserToReclamation(@PathVariable Long idRec,@PathVariable Long idUser){
        return reclamationService.affecterUserToReclamation(idRec,idUser);
    }


}
