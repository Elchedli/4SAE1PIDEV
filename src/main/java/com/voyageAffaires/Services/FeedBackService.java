package com.voyageAffaires.Services;


import com.voyageAffaires.entities.FeedBack;

import java.util.List;

public interface FeedBackService {
    FeedBack getFeedbackById(Long id);

    List<FeedBack> getAllFeedbacks();

    FeedBack getFeedbackByUser(Long idUser);

    FeedBack addFeedback(FeedBack feedBack);

    FeedBack updateFeedback(FeedBack feedBack,Long idReclamation);

    List<FeedBack> deleteFeedbackByid(Long idFeedback);

    List<FeedBack> deleteFeedbackByUser(Long idUser);

    void deleteAllFeedbacks();

    int nbrFeedbackByUser(Long idUser);

     List<FeedBack> affecterUserToFeedback(Long idFeedBack,Long idUser);

}
