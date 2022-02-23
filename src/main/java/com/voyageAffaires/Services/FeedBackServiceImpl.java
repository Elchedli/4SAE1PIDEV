package com.voyageAffaires.Services;

import com.voyageAffaires.Repositories.FeedBackRepository;
import com.voyageAffaires.Repositories.UserRepository;
import com.voyageAffaires.entities.FeedBack;
import com.voyageAffaires.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl implements FeedBackService{
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FeedBack getFeedbackById(Long id) {
        return feedBackRepository.findById(id).orElse(null);
    }

    @Override
    public List<FeedBack> getAllFeedbacks() {
        return feedBackRepository.findAll();
    }

    @Override
    public FeedBack getFeedbackByUser(Long idUser) {
        return feedBackRepository.getFeedBackByUser(userRepository.findById(idUser).orElse(null));
    }

    @Override
    public FeedBack addFeedback(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    @Override
    public FeedBack updateFeedback(FeedBack feedBack, Long idFeedBack) {
        FeedBack feed=this.getFeedbackById(idFeedBack);
        if(feedBack.getMessage()!=null)feed.setMessage(feedBack.getMessage());
        if(feedBack.getUser()!=null)feed.setUser(feedBack.getUser());
        return feedBackRepository.save(feed);
    }

    @Override
    public List<FeedBack> deleteFeedbackByid(Long idFeedback) {
        feedBackRepository.deleteById(idFeedback);
        return this.getAllFeedbacks();
    }

    @Override
    public List<FeedBack> deleteFeedbackByUser(Long idUser) {
        feedBackRepository.deleteByUser(userRepository.findById(idUser).orElse(null));
        return this.getAllFeedbacks();
    }

    @Override
    public void deleteAllFeedbacks() {
        feedBackRepository.deleteAll();
    }

    @Override
    public int nbrFeedbackByUser(Long idUser) {
        return feedBackRepository.nbrFeedbackByUser(idUser);
    }

    @Override
    public List<FeedBack> affecterUserToFeedback(Long idFeedBack, Long idUser) {
        FeedBack feed=this.getFeedbackById(idFeedBack);
        User us=userRepository.findById(idUser).orElse(null);
        feed.setUser(us);
        feedBackRepository.save(feed);
        return this.getAllFeedbacks();
    }
}
