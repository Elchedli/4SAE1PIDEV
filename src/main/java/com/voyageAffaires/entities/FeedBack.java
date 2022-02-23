package com.voyageAffaires.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_back")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_feed", nullable = false)
    private Long id;
    private String message;
    @ManyToOne
    @JsonIgnore
    private User user;




}
