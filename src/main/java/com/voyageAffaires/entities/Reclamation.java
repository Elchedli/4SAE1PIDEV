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
@Table(name = "reclamation")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rec", nullable = false)
    private Long id;
    private String message;

    @ManyToOne
    @JsonIgnore
    private User user;





}
