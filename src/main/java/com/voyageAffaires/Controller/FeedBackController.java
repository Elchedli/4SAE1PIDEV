package com.voyageAffaires.Controller;

import com.voyageAffaires.Services.FeedBackService;
import com.voyageAffaires.entities.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    @GetMapping("/all")
    public List<FeedBack> getAllfeedBacks(){
        return feedBackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public FeedBack getfeedBackById(@PathVariable Long id){
        return feedBackService.getFeedbackById(id);
    }

    @GetMapping("/user/{idUser}")
    public FeedBack getfeedBackByUser(@PathVariable Long idUser){
        return feedBackService.getFeedbackByUser(idUser);
    }

    @GetMapping("/user/nbrFeedback/{idUser}")
    public int nbrfeedBackByUser(@PathVariable Long idUser){
        return feedBackService.nbrFeedbackByUser(idUser);
    }

    @PostMapping("/add")
    public ResponseEntity<FeedBack> addfeedBack(@RequestBody FeedBack feedBack)throws Exception{
        if(feedBack.getDateFeedBack()==null){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Date must be not null");
        }else if(feedBack.getMessage().isEmpty()){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Message must be not empty");
        }
        return  new ResponseEntity<>(feedBackService.addFeedback(feedBack),HttpStatus.CREATED);
    }

    @PutMapping("/update/{idFeedback}")
    public ResponseEntity<FeedBack> updatefeedBack(@RequestBody FeedBack feedBack,@PathVariable  Long idFeedback) throws Exception{

        return new ResponseEntity<>(feedBackService.updateFeedback(feedBack,idFeedback),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public String deleteAllfeedBacks(){
        feedBackService.deleteAllFeedbacks();
        return "All deleted !!";
    }

    @DeleteMapping("/delete/{id}")
    public List<FeedBack> deletefeedBackById(@PathVariable Long id){
        feedBackService.deleteFeedbackByid(id);
        return this.getAllfeedBacks();
    }

    @DeleteMapping("/delete/user/{id}")
    public List<FeedBack> deletefeedBackByUser(@PathVariable Long id){
        feedBackService.deleteFeedbackByUser(id);
        return this.getAllfeedBacks();
    }

    @GetMapping("/affectUser/{idFeed}/{idUser}")
    public List<FeedBack> affecterUserTofeedBack(@PathVariable Long idFeed,@PathVariable Long idUser){
        return feedBackService.affecterUserToFeedback(idFeed,idUser);
    }


}
