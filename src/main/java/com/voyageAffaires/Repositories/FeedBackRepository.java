package com.voyageAffaires.Repositories;

import com.voyageAffaires.entities.FeedBack;

import com.voyageAffaires.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    FeedBack getFeedBackByUser(User user);
    FeedBack deleteByUser(User user);

    @Query("SELECT count(F) FROM  FeedBack F,User U WHERE  U.id=F.user.id and U.id=:id")
    int nbrFeedbackByUser(Long id);
}
