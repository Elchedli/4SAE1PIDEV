package com.voyageAffaires.Controller;

import com.voyageAffaires.Services.FeedBackService;
import com.voyageAffaires.entities.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public FeedBack addfeedBack(@RequestBody FeedBack feedBack){
        return  feedBackService.addFeedback(feedBack);
    }

    @PutMapping("/update/{idFeedback}")
    public FeedBack updatefeedBack(@RequestBody FeedBack feedBack,@PathVariable  Long idFeedback){
        return feedBackService.updateFeedback(feedBack,idFeedback);
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
